package fr.paragoumba.minediversity.economyapi;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import fr.paragoumba.minediversity.economyapi.objects.Account;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.*;
import java.util.UUID;
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

    /**
     * Crée un compte dans la base de données pour le joueur spécifié
     * @param player Joueur pour lequel il faut créer un compte
     */
    public static void createAccount(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("INSERT INTO `" + table +"` VALUES ('" + player.getUniqueId() + "', '" + player.getName() + "', 0);");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * Permet de récupèrer l'argent présent dans le porte-monnaie du joueur.
     * @param player Le joueur dont on veut connaître l'argent présent dans le porte-monnaie.
     * @return L'argent présent dans le porte-monnaie du joueur
     */
    public static double getWalletFunds(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT walletFunds FROM `" + table + "` WHERE player = " + player.getUniqueId().toString())){

            result.next();
            return result.getDouble(1);

        } catch (Exception e) {

            System.out.println("Wallet's funds' error.");
            e.printStackTrace();

        }

        System.out.println("Returning an error.");
        return 0.0;
    }

    /**
     * Permet de récupèrer l'argent présent sur le compte bancaire du joueur.
     * @param player Le joueur dont on veut connaître l'argent présent sur le compte bancaire du joueur.
     * @return L'argent présent dans le porte-monnaie du joueur
     */
    public static double getBankFunds(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT bankFunds FROM `" + table + "` WHERE player = " + player.getUniqueId().toString())){

            result.next();
            return result.getDouble(1);

        } catch (Exception e) {

            System.out.println("Bank's funds' error.");
            e.printStackTrace();

        }

        System.out.println("Returning an error.");
        return 0.0;
    }

    /**
     * Permet de récupèrer le dernier pseudo connu du possesseur du compte.
     * @param player Le joueur auquel appartient le compte.
     * @return Le dernier pseudo connu du possesseur du compte.
     */
    public static String getLastPseudo(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT lastPseudo FROM `" + table + "` WHERE player = " + player.getUniqueId().toString())){

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

    /**
     * Permet de définir l'argent présent dans le porte-monnaie du joueur.
     * @param player Le possesseur du compte.
     * @param funds L'argent à mettre dans le porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean setWalletFunds(Player player, double funds){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE `" + table + "` SET walletFunds = " + funds + " WHERE player = " + player.getUniqueId().toString());

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet d'ajouter de l'argent dans le porte-monnaie du joueur.
     * @param player Le possesseur du compte.
     * @param funds L'argent à ajouter au porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean addWalletFunds(Player player, double funds){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE `" + table + "` SET walletFunds = walletFunds + " + funds + " WHERE player = " + player.getUniqueId().toString());

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet de retirer de l'argent dans le porte-monnaie du joueur.
     * @param player Le possesseur du compte.
     * @param funds L'argent à retirer du porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean subWalletFunds(Player player, double funds){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE `" + table + "` SET walletFunds = walletFunds - " + funds + " WHERE player = " + player.getUniqueId().toString());

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet de définir l'argent présent sur le compte bancaire du joueur.
     * @param player Le possesseur du compte.
     * @param funds L'argent à mettre sur le compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean setBankFunds(Player player, double funds){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE `" + table + "` SET bankFunds = " + funds + " WHERE player = " + player.getUniqueId().toString());

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet d'ajouter de l'argent sur le compte bancaire du joueur.
     * @param player Le possesseur du compte.
     * @param funds L'argent à ajouter au compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean addBankFunds(Player player, double funds){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE `" + table + "` SET bankFunds = bankFunds + " + funds + " WHERE player = " + player.getUniqueId().toString());

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet de retirer de l'argent du compte bancaire du joueur.
     * @param player Le possesseur du compte.
     * @param funds L'argent à retirer du compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean subBankFunds(Player player, double funds){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE `" + table + "` SET bankFunds = bankFunds - " + funds + " WHERE player = " + player.getUniqueId().toString());

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    static void init(){

        Configuration config = plugin.getConfig();
        database = config.getString("database");
        url = "jdbc:mysql://" + config.getString("host") + ":" + config.getString("port") + "/" + database;
        login = config.getString("login");
        password = config.getString("password");
        table = config.getString("table");

        try(Connection connection = DriverManager.getConnection(url, login, password)){

            try(Statement state = connection.createStatement()){

                state.executeQuery("SELECT max(walletFunds) FROM `" + table + "`");

            } catch (MySQLSyntaxErrorException e){

                Statement state = connection.createStatement();
                state.executeUpdate("CREATE TABLE " + database + ".`" + table + "` (" +
                        "player TINYTEXT," +
                        "lastPseudo TINYTEXT," +
                        "walletFunds DOUBLE," +
                        "bankFunds DOUBLE" +
                        ") ENGINE = INNODB");

                state.close();
            }

            Bukkit.getLogger().log(Level.WARNING, "EconomyAPI: Database works. (" + url + ")");

        } catch (SQLException e) {

            Bukkit.getLogger().log(Level.WARNING, "EconomyAPI: Error in database connection. (" + url + ")");
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
