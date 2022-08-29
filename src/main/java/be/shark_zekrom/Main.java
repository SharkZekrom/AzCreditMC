package be.shark_zekrom;

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

        config.set("database.host", " ");
        config.set("database.database", " ");
        config.set("database.username", " ");
        config.set("database.password", " ");
        config.set("database.port", 0);

        config.options().copyDefaults(true);
        saveConfig();

        new Database(config);
        System.out.println("Enabled.");
    }

    @Override
    public void onDisable() {

    }



}
