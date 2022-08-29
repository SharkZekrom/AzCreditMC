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

        database(config);
        System.out.println("Enabled.");
    }

    @Override
    public void onDisable() {

    }

    public void database(FileConfiguration config) {

        Database.host = config.getString("host");
        Database.database = config.getString("database");
        Database.username = config.getString("username");
        Database.password = config.getString("password");
        Database.port = config.getInt("port");

    }

}
