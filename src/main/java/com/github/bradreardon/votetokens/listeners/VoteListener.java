package com.github.bradreardon.votetokens.listeners;

import com.evilmidget38.UUIDFetcher;
import com.github.bradreardon.votetokens.VoteTokens;
import com.github.bradreardon.votetokens.database.Log;
import com.github.bradreardon.votetokens.database.VotePlayer;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getLogger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 *
 * @author Brad Reardon <brad.jay.reardon@gmail.com>
 */
public class VoteListener implements Listener {
        
    private final VoteTokens plugin;

    public VoteListener(VoteTokens plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler(priority=EventPriority.NORMAL)
    public void onVoteEvent(VotifierEvent event) {
        Vote vote = event.getVote();
        UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList(vote.getUsername()));
        
        Map<String, UUID> response = null;
        try {
            response = fetcher.call();
        } catch (Exception e) {
            getLogger().warning("Exception while running UUIDFetcher");
            e.printStackTrace();
        }
        
        String uuid = response.get(vote.getUsername()).toString();
        VotePlayer player = plugin.getPlayerTable().getPlayer(uuid);

        player.setTokens(player.getTokens() + 1);
        plugin.getPlayerTable().update(player);
        
        String service;
        if (vote.getServiceName() != null) {
            service = vote.getServiceName();
        } else {
            service = "";
        }
        
        Log log = new Log();
        log.setLogDate(new Date());
        log.setLogType("vote");
        log.setPlayer(vote.getUsername());
        log.setUuid(uuid);
        log.setLogText(service);
        plugin.getLogTable().save(log);
        
        Player p = plugin.getServer().getPlayer(vote.getUsername());
        if(p != null) {
            p.sendMessage("§6Thanks for voting! §lYou now have: §r" + player.getTokens() + " Vote Tokens.");
            p.sendMessage("§6Type §f/vt rewards §6to see our list of rewards for voters.");
        }
    }
}
