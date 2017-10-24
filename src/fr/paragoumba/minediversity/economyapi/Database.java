package fr.paragoumba.minediversity.economyapi;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.logging.Level;

import static fr.paragoumba.minediversity.economyapi.EconomyAPI.plugin;

/**
 * Created by Paragoumba on 13/06/2017.
 */

public class Database {

    private Database(){}

    private static String database;
    private static String url;
    private static String login;
    private static String password;
    private static String table;

    public static void createTicket(Player player, String message, Location loc){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("INSERT INTO `" + table +"` VALUES (" + (getLastID() + 1) + ", '" + player.getUniqueId() + "', '" + player.getName() + "', '" + message + "', '" + loc.getWorld().getUID() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "', NULL);");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /*static ArrayList<Ticket> getTickets(){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM `" + table + "`")){

            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){

                String[] coos = result.getString("location").split(",");

                Player helper = result.getString("helper") != null ? Bukkit.getPlayer(UUID.fromString(result.getString("helper"))) : null;
                tickets.add(new Ticket(result.getInt("id"), Bukkit.getPlayer(UUID.fromString(result.getString("player"))), result.getString("message"), new Location(Bukkit.getWorld(UUID.fromString(coos[0])), Double.parseDouble(coos[1]), Double.parseDouble(coos[2]), Double.parseDouble(coos[3])), helper));

            }

            return tickets;

        } catch (Exception e) {

            System.out.println("Ticket's error.");
            e.printStackTrace();

        }

        System.out.println("Returning an error.");
        return null;
    }*/

    /*static Ticket getTicket(int id){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM `" + table + "` WHERE id = " + id)){

            result.next();
            String[] coos = result.getString("location").split(",");
            return new Ticket(result.getInt("id"), Bukkit.getPlayer(UUID.fromString(result.getString("player"))), result.getString("message"), new Location(Bukkit.getWorld(UUID.fromString(coos[0])), Double.parseDouble(coos[1]), Double.parseDouble(coos[2]), Double.parseDouble(coos[3])), result.getString("helper") != null ? Bukkit.getPlayer(UUID.fromString(result.getString("helper"))) : null);

        } catch (Exception e) {

            System.out.println("Ticket's error.");
            e.printStackTrace();

        }

        System.out.println("Returning an error.");
        return null;
    }*/

    /*static void removeTicket(int id){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("DELETE FROM `" + table + "` WHERE id = " + id);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }*/

    public static int getLastID(){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT max(id) FROM `" + table + "`")){

            int lastId = 0;

            while (result.next()){

                lastId = result.getInt(1);

            }

            return lastId;

        } catch (Exception e) {

            System.out.println("Error in getting last id.");
            e.printStackTrace();

        }

        return 0;

    }

    public static String getLastPseudo(int id){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT lastPseudo FROM `" + table + "` WHERE id = " + id)){

            String lastPseudo = "NULL";

            while (result.next()){

                lastPseudo = result.getString(1);

            }

            return lastPseudo;

        } catch (Exception e) {

            System.out.println("Error in getting last pseudo.");
            e.printStackTrace();

        }

        return "NULL";

    }

    /*static boolean setHelper(int id, UUID uuid){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            String uniqueId = uuid != null ? '\'' + uuid.toString() + '\'' : null;

            state.executeUpdate("UPDATE `" + table + "` SET helper=" + uniqueId + " WHERE id=" + id);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }*/

    static void init(){

        Configuration config = plugin.getConfig();
        database = config.getString("database");
        url = "jdbc:mysql://" + config.getString("host") + ":" + config.getString("port") + "/" + database;
        login = config.getString("login");
        password = config.getString("password");
        table = config.getString("table");

        try(Connection connection = DriverManager.getConnection(url, login, password)){

            try(Statement state = connection.createStatement()){

                state.executeQuery("SELECT * FROM `" + table + "`");

            } catch (MySQLSyntaxErrorException e){

                Statement state = connection.createStatement();
                state.executeUpdate("CREATE TABLE " + database + ".`" + table + "` (" +
                        "player TINYTEXT," +
                        "lastPseudo TINYTEXT," +
                        "funds " +
                        ") ENGINE = INNODB");

                state.close();
            }

            Bukkit.getLogger().log(Level.WARNING, "Tickets: Database works. (" + url + ")");

        } catch (SQLException e) {

            Bukkit.getLogger().log(Level.WARNING, "Tickets: Error in database connection. (" + url + ")");
            e.printStackTrace();

        }
    }

    static void reset(){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("DELETE FROM `" + table + "`");

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }
}
