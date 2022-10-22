package be.shark_zekrom;

import be.shark_zekrom.Database;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Commands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            if (args[0].equalsIgnoreCase("add")) {

                try {
                    addMoney(null, args[1], Double.valueOf(args[2]));
                    System.out.println("§a§credit added to " + args[1] + " : " + args[2]);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (args[0].equalsIgnoreCase("remove")) {

                try {
                    removeMoney(null, args[1], Double.valueOf(args[2]));
                    System.out.println("§a§credit removed to " + args[1] + " : " + args[2]);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {

            Player player = (Player) sender;
            if (args.length == 2) {

                if (player.hasPermission("azcreditmc.gui")) {
                    if (args[0].equalsIgnoreCase("gui")) {
                        try {
                            Gui.gui(player, args[1]);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (args.length == 3) {

                if (player.hasPermission("azcreditmc.add")) {
                    if (args[0].equalsIgnoreCase("add")) {

                        try {
                            addMoney(player, args[1], Double.valueOf(args[2]));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (player.hasPermission("azcreditmc.remove")) {
                    if (args[0].equalsIgnoreCase("remove")) {

                        try {
                            removeMoney(player, args[1], Double.valueOf(args[2]));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void addMoney(Player sender, String target, Double money) throws SQLException {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET money = money + ? WHERE name = ?");
            ps.setDouble(1, money);
            ps.setString(2, target);
            ps.execute();

            if (sender != null) {
                sender.sendMessage("points boutique update to " + money);
            }

    }

    public static void removeMoney(Player sender, String target, Double money) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE users SET money = money - ? WHERE name = ?");
        ps.setDouble(1, money);
        ps.setString(2, target);
        ps.execute();

        if (sender != null) {
            sender.sendMessage("points boutique update to " + money);
        }
    }
}