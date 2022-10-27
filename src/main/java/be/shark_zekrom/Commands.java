package be.shark_zekrom;

import be.shark_zekrom.Database;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor , TabExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            if (args[0].equalsIgnoreCase("add")) {

                try {
                    addCredit(null, args[1], Double.valueOf(args[2]));
                    System.out.println(Main.getInstance().getConfig().getString("ConsoleCreditAdded").replaceAll("%player%", args[1]).replaceAll("%credits%", args[2]));

                    if (Database.getMoney(args[1]) == null) {
                        Bukkit.getPlayer(args[1]).sendMessage(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("UnregisteredPlayerConsole"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (args[0].equalsIgnoreCase("remove")) {

                try {
                    removeCredit(null, args[1], Double.valueOf(args[2]));
                    System.out.println(Main.getInstance().getConfig().getString("ConsoleCreditRemoved").replaceAll("%player%", args[1]).replaceAll("%credits%", args[2]));

                    if (Database.getMoney(args[1]) == null) {
                        Bukkit.getPlayer(args[1]).sendMessage(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("UnregisteredPlayerConsole"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {

            Player player = (Player) sender;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("azcreditmc.reload")) {
                        reload(player);
                    } else {
                        player.sendMessage(Main.getInstance().getConfig().getString("NoPermission"));
                    }
                } else {
                    player.sendMessage(Main.getInstance().getConfig().getString("NoPermission"));

                }
            }

            if (args.length == 2) {

                if (player.hasPermission("azcreditmc.gui")) {
                    if (args[0].equalsIgnoreCase("gui")) {
                        try {
                            Gui.gui(player, args[1]);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    player.sendMessage(Main.getInstance().getConfig().getString("NoPermission"));

                }
            }
            if (args.length == 3) {

                if (player.hasPermission("azcreditmc.add")) {
                    if (args[0].equalsIgnoreCase("add")) {

                        try {
                            addCredit(player, args[1], Double.valueOf(args[2]));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    player.sendMessage(Main.getInstance().getConfig().getString("NoPermission"));

                }
                if (player.hasPermission("azcreditmc.remove")) {
                    if (args[0].equalsIgnoreCase("remove")) {

                        try {
                            removeCredit(player, args[1], Double.valueOf(args[2]));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    player.sendMessage(Main.getInstance().getConfig().getString("NoPermission"));

                }
            }
        }
        return false;
    }

    public static void addCredit(Player sender, String target, Double money) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE users SET money = money + ? WHERE name = ?");
        ps.setDouble(1, money);
        ps.setString(2, target);
        ps.execute();

        if (sender != null) {
            sender.sendMessage("points boutique update to " + money);
        }


    }

    public static void removeCredit(Player sender, String target, Double money) throws SQLException {

        Connection connection = Database.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE users SET money = money - ? WHERE name = ?");
        ps.setDouble(1, money);
        ps.setString(2, target);
        ps.execute();

        if (sender != null) {
            sender.sendMessage("points boutique update to " + money);
        }
    }

    public static void setCredit(Player sender, String target, Double money) throws SQLException {

        Connection connection = Database.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE users SET money = ? WHERE name = ?");
        ps.setDouble(1, money);
        ps.setString(2, target);
        ps.execute();

        if (sender != null) {
            sender.sendMessage("points boutique update to " + money);
        }
    }


    public static void reload(Player player) {
        try {
            Main.getInstance().getConfig().load(new File(Main.getInstance().getDataFolder(), "config.yml"));
            player.sendMessage(Main.getInstance().getConfig().getString("Reload"));

        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 1) {
            arguments.add("reload");
            arguments.add("gui");
            arguments.add("add");
            arguments.add("remove");

        }
        return arguments;
    }

}