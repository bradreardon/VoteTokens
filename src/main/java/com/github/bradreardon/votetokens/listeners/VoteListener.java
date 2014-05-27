package com.github.bradreardon.votetokens.listeners;

import com.github.bradreardon.votetokens.VoteTokens;
import com.github.bradreardon.votetokens.database.Log;
import com.github.bradreardon.votetokens.database.VotePlayer;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import java.util.Date;
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
        VotePlayer player = plugin.getPlayerTable().getPlayer(vote.getUsername());

        player.setTokens(player.getTokens() + 1);
        plugin.getPlayerTable().update(player);
        
        Log log = new Log();
        log.setLogDate(new Date());
        log.setLogType("vote");
        log.setPlayer(vote.getUsername());
        log.setLogText(vote.getServiceName());
        plugin.getLogTable().save(log);
        
        Player p = plugin.getServer().getPlayer(vote.getUsername());
        if(p != null)
            p.sendMessage("§6Thanks for voting! Type §f/vt rewards §6to see our list of rewards for voters.");
    }
}
