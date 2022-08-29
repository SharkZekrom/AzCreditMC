package be.shark_zekrom;

import be.shark_zekrom.Database;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Commands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 3) {

            if (player.hasPermission("negalium.money.add")) {
                if (args[0].equalsIgnoreCase("add")) {

                    Player target = Bukkit.getPlayer(args[1]);

                    try {
                        addMoney(player, target, Double.valueOf(args[2]));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (player.hasPermission("negalium.money.remove")) {
                if (args[0].equalsIgnoreCase("remove")) {
                    Player target = Bukkit.getPlayer(args[1]);

                    try {
                        removeMoney(player, target, Double.valueOf(args[2]));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }


    public static void addMoney(Player sender, Player target, Double string) throws SQLException {
        String money = Database.getMoney(target);
        if (!money.equals("§c§lJoueur non inscrit")) {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET money = ? WHERE name = ?");
            ps.setDouble(1, Double.parseDouble(money) + string);
            ps.setString(2, target.getName());
            ps.execute();

            sender.sendMessage("points boutique update to " + (Double.parseDouble(money) + string));

        }
    }

    public static void removeMoney(Player sender, Player target, Double string) throws SQLException {
        String money = Database.getMoney(target);
        if (!money.equals("§c§lJoueur non inscrit")) {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET money = ? WHERE name = ?");
            ps.setDouble(1, Double.parseDouble(money) - string);
            ps.setString(2, target.getName());
            ps.execute();

            sender.sendMessage("points boutique update to " + (Double.parseDouble(money) - string));

        }
    }
}