package be.shark_zekrom;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new Gui(), this);

        this.getCommand("azmoneymc").setExecutor(new Commands());


        System.out.println("Enabled.");
    }

    @Override
    public void onDisable() {

    }
}
