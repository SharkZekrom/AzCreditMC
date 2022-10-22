package be.shark_zekrom;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new Gui(), this);

        this.getCommand("azcreditmc").setExecutor(new Commands());


        FileConfiguration config = getConfig();
        config.addDefault("database.host", " ");
        config.addDefault("database.database", " ");
        config.addDefault("database.username", " ");
        config.addDefault("database.password", " ");
        config.addDefault("database.port", 3306);

        config.addDefault("Reload", "§c[AzCreditMC] Successfully reloaded!");
        config.addDefault("NoPermission", "§cYou do not have permission to use that");
        config.addDefault("GuiCreditEditing", "§7Credits");
        config.addDefault("GuiCredit", "update point %player%");
        config.addDefault("ConsoleCreditAdded", "%credits% credits have been added to %player%");
        config.addDefault("ConsoleCreditRemoved", "%credits% credits have been removed from %player%");
        config.addDefault("UnregisteredPlayer", "§cUnregistered player");
        config.addDefault("UnregisteredPlayerConsole", "§cPlease create a account on https://shark_zekrom.dev");

        config.options().copyDefaults(true);

        saveConfig();


        database(config.getString("database.host"),
                config.getString("database.database"),
                config.getString("database.username"),
                config.getString("database.password"),
                config.getInt("database.port"));

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
