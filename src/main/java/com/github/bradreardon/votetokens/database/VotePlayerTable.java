/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.bradreardon.votetokens.database;

import com.github.bradreardon.votetokens.VoteTokens;

/**
 *
 * @author Brad Reardon <brad.jay.reardon@gmail.com>
 */
public class VotePlayerTable {
    
    private final VoteTokens plugin;
    
    public VotePlayerTable(VoteTokens plugin) {
        this.plugin = plugin;
    }
    
    public VotePlayer getPlayer(String username) {
        VotePlayer vp = plugin.getDatabase().find(VotePlayer.class).where().ieq("player", username).query().findUnique();
        if (vp == null) {
            vp = new VotePlayer();
            vp.setPlayer(username);
            vp.setTokens(0);
            save(vp);
            return vp;
        }
        return vp;
    }
    
    public void update(VotePlayer player) {
        plugin.getDatabase().update(player);
    }
    
    public void save(VotePlayer player) {
        plugin.getDatabase().save(player);
    }
    
}
