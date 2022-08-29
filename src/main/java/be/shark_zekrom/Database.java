package be.shark_zekrom;

import org.bukkit.configuration.file.FileConfiguration;
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

    public static String getMoney(Player player) throws SQLException {

        Connection connection = Database.getConnection();

        PreparedStatement preparedStatementMoney = connection.prepareStatement("select * from users where name = ?");
        preparedStatementMoney.setString(1, player.getName());
        ResultSet rsMoney = preparedStatementMoney.executeQuery();



        String money = "";

        if (rsMoney.next()) {
            money = String.valueOf(rsMoney.getDouble("money"));
        } else {
            money = "§c§lJoueur non inscrit";
        }

        return money;


    }

    public Database(FileConfiguration config) {

        host = config.getString("host");
        database = config.getString("database");
        username = config.getString("username");
        password = config.getString("password");
        port = config.getInt("port");

    }
}
