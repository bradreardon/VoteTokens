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

@Entity()
@Table(name = "vt_rewards")
public class Reward {
    
    @Id
    private int id;
    
    @NotNull
    private String name;
    
    @NotNull
    private String itemId;
    
    @NotNull
    private String itemAmt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemAmt() {
        return itemAmt;
    }

    public void setItemAmt(String itemAmt) {
        this.itemAmt = itemAmt;
    }
    
}
