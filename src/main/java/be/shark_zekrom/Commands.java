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

            if (player.hasPermission("azmoneymc.add")) {
                if (args[0].equalsIgnoreCase("add")) {

                    try {
                        addMoney(player, args[1], Double.valueOf(args[2]));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (player.hasPermission("azmoneymc.remove")) {
                if (args[0].equalsIgnoreCase("remove")) {

                    try {
                        removeMoney(player, args[1], Double.valueOf(args[2]));

                    } catch (Exception e) {
                        e.printStackTrace();
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

            sender.sendMessage("points boutique update to " + money);


    }

    public static void removeMoney(Player sender, String target, Double money) throws SQLException {
        Connection connection = Database.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE users SET money = money - ? WHERE name = ?");
        ps.setDouble(1, money);
        ps.setString(2, target);
        ps.execute();

        sender.sendMessage("points boutique update to " + money);

    }
}