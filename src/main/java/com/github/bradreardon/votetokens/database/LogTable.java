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
public class LogTable {
    
    private final VoteTokens plugin;
    
    public LogTable(VoteTokens plugin) {
        this.plugin = plugin;
    }
    
    public void save(Log log) {
        plugin.getDatabase().save(log);
    }
    
}
