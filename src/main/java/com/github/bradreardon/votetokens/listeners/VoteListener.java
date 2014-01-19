package com.github.bradreardon.votetokens.listeners;

import com.github.bradreardon.votetokens.VoteTokens;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
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
    }
}
