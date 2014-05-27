package com.github.bradreardon.votetokens;

import com.github.bradreardon.votetokens.database.Log;
import com.github.bradreardon.votetokens.database.LogTable;
import com.github.bradreardon.votetokens.database.VotePlayer;
import com.github.bradreardon.votetokens.database.VotePlayerTable;
import com.github.bradreardon.votetokens.database.Reward;
import com.github.bradreardon.votetokens.database.RewardTable;
import com.github.bradreardon.votetokens.listeners.VoteListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.PersistenceException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VoteTokens extends JavaPlugin {

    private static VoteListener voteListener;

    private LogTable logTable;
    private VotePlayerTable playerTable;
    private RewardTable rewardTable;

    @Override
    public void onEnable() {
        initDB();
        logTable = new LogTable(this);
        playerTable = new VotePlayerTable(this);
        rewardTable = new RewardTable(this);
        voteListener = new VoteListener(this);
        getLogger().info("Now listening to Votifier events.");
    }

    @Override
    public void onDisable() {
        voteListener = null;
        logTable = null;
        playerTable = null;
        rewardTable = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("vt")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
            } else {
                Player player = (Player) sender;
                VotePlayer vp = playerTable.getPlayer(player.getName());
                
                if(args.length == 0) {
                    player.sendMessage("§6§lYou currently have: §r" + vp.getTokens() + " Vote Tokens.");
                    player.sendMessage("§6Type §f/vt rewards §6to list all rewards.");
                    return true;
                }
                
                if(args[0].equalsIgnoreCase("rewards")) {
                    List<Reward> rewards = rewardTable.getAllRewards().findList();
                    player.sendMessage("§6§lRewards:");
                    for (Reward r : rewards) {
                        player.sendMessage(String.format("§6[ID: %d]§f§l %s §r(%d Tokens)", new Object[] {r.getId(), r.getName(), r.getCost()}));
                    }
                    player.sendMessage("§6Type §f/vt buy <id> §6to purchase a reward.");
                } else if(args[0].equalsIgnoreCase("buy")) {
                    int id = -1;
                    if(args.length != 2) {
                        player.sendMessage("§6Invalid syntax. Type §f/vt buy <id> §6to purchase a reward.");
                        return true;
                    }
                    
                    try {
                        id = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("§6Invalid syntax. Type §f/vt buy <id> §6to purchase a reward.");
                        return true;
                    }
                    
                    boolean canPurchase;
                    try {
                        canPurchase = rewardTable.canPurchaseReward(id, vp.getTokens());
                    } catch (Exception e) {
                        canPurchase = false;
                    }
                    
                    if(!canPurchase) {
                        player.sendMessage("§6You don't have enough vote tokens for that! Type §f/vt §6to see your balance.");
                        return true;
                    }
                    
                    if(player.getInventory().firstEmpty() == -1) {
                        player.sendMessage("§6Your inventory is full. Clear some space and try that again.");
                        return true;
                    }
                    
                    Reward r = rewardTable.getReward(id);
                    Log log = new Log();
                    log.setLogType("purchase");
                    log.setLogDate(new Date());
                    log.setPlayer(player.getName());
                    log.setLogText(r.getName());
                    logTable.save(log);
                    
                    player.getInventory().addItem(r.getItemStack());
                    vp.setTokens(vp.getTokens() - r.getCost());
                    playerTable.update(vp);
                    player.sendMessage("§6Purchase successful. Your new balance is §r" + vp.getTokens() + " Vote Tokens.");
                } else {
                    player.sendMessage("§6Unknown subcommand. Type §f/vt §6 to check your balance.");
                }
            }
            return true;
        }
        return false;
    }

    private void initDB() {
        try {
            getDatabase().find(Log.class).findRowCount();
            getDatabase().find(VotePlayer.class).findRowCount();
            getDatabase().find(Reward.class).findRowCount();
        } catch (PersistenceException ex) {
            getLogger().log(Level.INFO, "Initializing database tables.");
            installDDL();
        }
    }

    @Override
    public ArrayList<Class<?>> getDatabaseClasses() {
        ArrayList<Class<?>> dbClasses = new ArrayList();
        dbClasses.add(Log.class);
        dbClasses.add(VotePlayer.class);
        dbClasses.add(Reward.class);
        return dbClasses;
    }

    public LogTable getLogTable() {
        return logTable;
    }

    public VotePlayerTable getPlayerTable() {
        return playerTable;
    }

    public RewardTable getRewardTable() {
        return rewardTable;
    }
}
