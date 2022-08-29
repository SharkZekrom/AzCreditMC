package be.shark_zekrom;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new Gui(), this);

        this.getCommand("azmoneymc").setExecutor(new Commands());


        FileConfiguration config = getConfig();
        config.addDefault("database.host", " ");
        config.addDefault("database.database", " ");
        config.addDefault("database.username", " ");
        config.addDefault("database.password", " ");
        config.addDefault("database.port", 3306);


        config.options().copyDefaults(true);

        saveConfig();


        database(config.getString("database.host"), config.getString("database.database"),config.getString("database.username"),config.getString("database.password"),config.getInt("database.port"));

        System.out.println("Enabled.");
    }

    @Override
    public void onDisable() {

    }

    public void database(String host, String database, String username, String password, int port) {
        Database.host = host;
        Database.database = database;
        Database.username = username;
        Database.password = password;
        Database.port = port;

    }

}
