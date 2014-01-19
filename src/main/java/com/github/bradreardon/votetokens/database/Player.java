/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.bradreardon.votetokens.database;

import com.avaje.ebean.validation.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Brad Reardon <brad.jay.reardon@gmail.com>
 */

// TODO: PLAYER RELATIONS

@Entity()
@Table(name = "vt_players")
public class Player {
    
    @Id
    private int id;
    
    @NotNull
    private int player;
    
    @NotNull
    private int tokens;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
    
}
