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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
    private String itemName;
    
    @NotNull
    private int itemAmt;
    
    @NotNull
    private int cost;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemAmt() {
        return itemAmt;
    }

    public void setItemAmt(int itemAmt) {
        this.itemAmt = itemAmt;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public ItemStack getItemStack() {
        return new ItemStack(Material.getMaterial(getItemName()), getItemAmt());
    }
    
}
