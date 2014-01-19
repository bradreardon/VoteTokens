package com.github.bradreardon.votetokens;

import com.github.bradreardon.votetokens.database.Log;
import com.github.bradreardon.votetokens.database.Player;
import com.github.bradreardon.votetokens.database.Reward;
import com.github.bradreardon.votetokens.listeners.VoteListener;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.persistence.PersistenceException;
import org.bukkit.plugin.java.JavaPlugin;

public class VoteTokens extends JavaPlugin {
    
    private static VoteListener voteListener;

    public void onEnable() {
        initDB();
        voteListener = new VoteListener(this);
        getLogger().info("Now listening to Votifier events.");
    }
    
    public void onDisable() {
        voteListener = null;
    }
    
    private void initDB() {
        try {
            getDatabase().find(Log.class).findRowCount();
            getDatabase().find(Player.class).findRowCount();
            getDatabase().find(Reward.class).findRowCount();
        } catch(PersistenceException ex) {
            getLogger().log(Level.INFO, "Initializing database tables.");
            installDDL();
        }
    }
    
    public ArrayList<Class<?>> getDatabaseClasses() {
        ArrayList<Class<?>> dbClasses = new ArrayList();
        dbClasses.add(Log.class);
        dbClasses.add(Player.class);
        dbClasses.add(Reward.class);
        return dbClasses;
    }
}

