/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.bradreardon.votetokens.database;

import com.avaje.ebean.Query;
import com.github.bradreardon.votetokens.VoteTokens;

/**
 *
 * @author Brad Reardon <brad.jay.reardon@gmail.com>
 */
public class RewardTable {
    
    private final VoteTokens plugin;
    
    public RewardTable(VoteTokens plugin) {
        this.plugin = plugin;
    }
    
    public Reward getReward(int id) {
        return plugin.getDatabase().find(Reward.class).where().eq("id", id).query().findUnique();
    }
    
    public boolean canPurchaseReward(int id, int tokens) {
        Reward reward = plugin.getDatabase().find(Reward.class).where().eq("id", id).query().findUnique();
        if(reward.getCost() > tokens)
            return false;
        return true;
    }
    
    public Query<Reward> getAllRewards() {
        return plugin.getDatabase().find(Reward.class).orderBy("cost");
    }
    
    public void save(Reward reward) {
        plugin.getDatabase().save(reward);
    }
    
}
