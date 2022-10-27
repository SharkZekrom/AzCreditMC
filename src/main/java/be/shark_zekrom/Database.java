package be.shark_zekrom;

import org.bukkit.entity.Player;

import java.sql.*;

public class Database {

    public static String host, database, username, password;
    public static int port;


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException e) {
            return null;
        }
    }

    public static String getMoney(String player) throws SQLException {

        Connection connection = Database.getConnection();

        PreparedStatement preparedStatementMoney = connection.prepareStatement("select * from users where name = ?");
        preparedStatementMoney.setString(1, player);
        ResultSet rsMoney = preparedStatementMoney.executeQuery();



        String money = null;

        if (rsMoney.next()) {
            money = String.valueOf(rsMoney.getDouble("money"));
        }

        return money;


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

}
