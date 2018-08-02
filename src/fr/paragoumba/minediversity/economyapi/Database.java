package fr.paragoumba.minediversity.economyapi;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import static fr.paragoumba.minediversity.economyapi.EconomyAPI.plugin;

/**
 * Created by Paragoumba on 13/06/2017.
 */

public class Database {

    private Database(){}

    private static String database, url, login, password, playersBankTable, entBankTable, tombolaTable, tombolaWinTable;

    /**
     * Crée un compte dans la base de données pour le joueur spécifié.
     * @param player Joueur pour lequel il faut créer un compte.
     */
    public static void createAccount(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("INSERT INTO " + database + ".`" + playersBankTable +"` VALUES ('" + player.getUniqueId() + "', '" + player.getName() + "', 0.0, 0.0);");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * Permet de savoir si le joueur a déjà un compte bancaire.
     * @param player Le joueur dont on veut vérifier le compte bancaire.
     * @return true si le joueur a déjà un compte, sinon false.
     */
    public static boolean playerHasAccount(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM " + database + ".`" + playersBankTable + "` WHERE player = '" + player.getUniqueId() + "'")){

            return result.next();

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Error in getting player's account.");
            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet de récupèrer le dernier pseudo connu du possesseur du compte.
     * @param player Le joueur auquel appartient le compte.
     * @return Le dernier pseudo connu du possesseur du compte.
     */
    public static String getLastPseudo(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT lastPseudo FROM " + database + ".`" + playersBankTable + "` WHERE player = '" + player.getUniqueId() + "'")){

            String lastPseudo = "NULL";

            while (result.next()){

                lastPseudo = result.getString("lastPseudo");

            }

            return lastPseudo;

        } catch (Exception e) {

            System.out.println("Error in getting last pseudo.");
            e.printStackTrace();

        }

        return "NULL";

    }

    /**
     * Permet de récupèrer l'argent présent dans le porte-monnaie du joueur.
     * @param player Le joueur dont on veut connaître l'argent présent dans le porte-monnaie.
     * @return L'argent présent dans le porte-monnaie du joueur.
     */
    public static double getWalletFunds(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT walletFunds FROM " + database + ".`" + playersBankTable + "` WHERE player = '" + player.getUniqueId() + "'")){

            result.next();
            return result.getDouble("walletFunds");

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Wallet's funds' error.");
            e.printStackTrace();

        }

        return 0.0;
    }
    
    /**
     * Permet de définir l'argent présent dans le porte-monnaie du joueur.
     * @param player Le possesseur du compte.
     * @param amount L'argent à mettre dans le porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean setWalletFunds(Player player, double amount){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + playersBankTable + "` SET walletFunds = " + amount + " WHERE player = '" + player.getUniqueId() + "'");

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet d'ajouter de l'argent dans le porte-monnaie du joueur.
     * @param player Le possesseur du compte.
     * @param amount L'argent à ajouter au porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean addWalletFunds(Player player, double amount){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + playersBankTable + "` SET walletFunds = walletFunds + " + amount + " WHERE player = '" + player.getUniqueId() + "'");

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet de retirer de l'argent dans le porte-monnaie du joueur.
     * @param player Le possesseur du compte.
     * @param amount L'argent à retirer du porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean subWalletFunds(Player player, double amount){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + playersBankTable + "` SET walletFunds = walletFunds - " + amount + " WHERE player = '" + player.getUniqueId() + "'");

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet de récupèrer l'argent présent sur le compte bancaire du joueur.
     * @param player Le joueur dont on veut récupèrer le solde du compte bancaire.
     * @return L'argent présent sur le compte bancaire du joueur.
     */
    public static double getPlayerBankFunds(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT bankFunds FROM " + database + ".`" + playersBankTable + "` WHERE player = '" + player.getUniqueId() + "'")){

            result.next();
            return result.getDouble("bankFunds");

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, playersBankTable + "'s funds' error.");
            e.printStackTrace();

        }

        return 0.0;
    }
    
    /**
     * Permet de récupèrer l'argent présent sur le compte bancaire de l'entreprise.
     * @param name Le nom de l'entreprise dont on veut récupèrer le solde du compte bancaire.
     * @return L'argent présent sur le compte bancaire de l'entreprise.
     */
    public static double getEntBankFunds(String name){

        name = patch(name);

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT bankFunds FROM " + database + ".`" + entBankTable + "` WHERE name = " + name)){

            result.next();
            return result.getDouble("bankFunds");

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, entBankTable + "'s funds' error.");
            e.printStackTrace();

        }

        return 0.0;
    }
    
    /**
     * Permet de définir l'argent présent sur le compte bancaire du joueur.
     * @param player Le possesseur du compte.
     * @param amount L'argent à mettre sur le compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean setPlayerBankFunds(Player player, double amount){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + playersBankTable + "` SET bankFunds = " + amount + " WHERE player = '" + player.getUniqueId() + "'");

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet de définir l'argent présent sur le compte bancaire de l'entreprise.
     * @param name Le nom de l'entreprise.
     * @param amount L'argent à mettre sur le compte bancaire de l'entreprise.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean setEntBankFunds(String name, double amount){

        name = patch(name);

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + entBankTable + "` SET bankFunds = " + amount + " WHERE name = " + name);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet d'ajouter de l'argent sur le compte bancaire du joueur.
     * @param player Le possesseur du compte.
     * @param amount L'argent à ajouter au compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean addPlayerBankFunds(Player player, double amount){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + playersBankTable + "` SET bankFunds = bankFunds + " + amount + " WHERE player = '" + player.getUniqueId() + "'");

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet d'ajouter de l'argent sur le compte bancaire d'une entreprise.
     * @param name Le nom de l'entreprise.
     * @param amount L'argent à ajouter au compte bancaire de l'entreprise.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean addEntBankFunds(String name, double amount){

        name = patch(name);

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + entBankTable + "` SET bankFunds = bankFunds + " + amount + " WHERE name = " + name);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet de retirer de l'argent du compte bancaire du joueur.
     * @param player Le possesseur du compte.
     * @param amount L'argent à retirer du compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean subPlayerBankFunds(Player player, double amount){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + playersBankTable + "` SET bankFunds = bankFunds - " + amount + " WHERE player = '" + player.getUniqueId() + "'");

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Permet de retirer de l'argent du compte bancaire de l'entreprise.
     * @param name Le nom de l'entreprise.
     * @param amount L'argent à retirer du compte bancaire de l'entreprise.
     * @return true si la transaction a réussi, sinon false.
     */
    public static boolean subEntBankFunds(String name, double amount){

        name = patch(name);

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + entBankTable + "` SET bankFunds = bankFunds - " + amount + " WHERE name = " + name);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    public static LinkedList<AbstractMap.SimpleEntry<String, Double>> getAllBankFunds(){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT lastPseudo, bankFunds FROM " + database + ".`" + playersBankTable + "`")){

            LinkedList<AbstractMap.SimpleEntry<String, Double>> funds = new LinkedList<>();

            while (result.next()){

                funds.add(new AbstractMap.SimpleEntry<>(result.getString("lastPseudo"), result.getDouble("bankFunds")));

            }

            return funds;

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Error in getting bank's funds.");
            e.printStackTrace();

        }

        return new LinkedList<>();
    }

    public static boolean startTombola(){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("INSERT INTO " + database + ".`" + tombolaWinTable + "` VALUES (" + getLastTombolaWinId() + 1 + ", NULL, 0, FALSE)");
            return true;

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, entBankTable + "'s funds' error.");
            e.printStackTrace();

        }

        return false;

    }

    public static double getTombolaFunds(int id){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT funds FROM " + database + ".`" + tombolaWinTable + "` WHERE id = " + id)){

            result.next();
            return result.getDouble("funds");

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, entBankTable + "'s funds' error.");
            e.printStackTrace();

        }

        return 0.0;
    }

    public static boolean setTombolaFunds(int id, double amount){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + tombolaWinTable + "` SET funds = " + amount + " WHERE id = " + id);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    public static boolean addTombolaFunds(int id, double amount){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + tombolaWinTable + "` SET funds = funds + " + amount + " WHERE id = " + id);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    public static boolean subTombolaFunds(int id, double amount){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + tombolaWinTable + "` SET funds = funds - " + amount + " WHERE id = " + id);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    public static boolean addTombolaParticipant(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("INSERT INTO " + database + ".`" + tombolaTable + "` VALUES ('" + player.getUniqueId() + "')");
            return true;

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Error in adding a participant to the tombola.");
            e.printStackTrace();

        }

        return false;
    }

    public static int getTombolaId(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT id FROM " + database + ".`" + tombolaTable + "` WHERE winner = " + player.getUniqueId())){

            result.next();
            return result.getInt(0);

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Error in getting tombola's participants.");
            e.printStackTrace();

        }

        return -1;
    }

    public static List<Player> getTombolaParticipants(){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT player FROM " + database + ".`" + tombolaTable + "`")){

            List<Player> participants = new ArrayList<>();

            while (result.next()){

                participants.add(Bukkit.getPlayer(result.getString("player")));

            }

            return participants;

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Error in getting tombola's participants.");
            e.printStackTrace();

        }

        return new ArrayList<>();
    }

    public static boolean getTombolaParticipant(Player player){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT player FROM " + database + ".`" + tombolaTable + "` WHERE player = '" + player.getUniqueId() + "'")){

            return result.next();

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Error in getting tombola's participant.");
            e.printStackTrace();

        }

        return false;
    }

    public static boolean setTombolaWinner(Player winner){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + tombolaWinTable + "` SET winner = " + winner.getUniqueId().toString() + " WHERE id = " + getLastTombolaWinId());

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    public static boolean setTombolaWon(){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("UPDATE " + database + ".`" + tombolaWinTable + "` SET won = " + true + " WHERE id = " + getLastTombolaWinId());

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    public static ArrayList<String> getTombolaWinners(){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT winner FROM " + database + ".`" + tombolaWinTable + "` WHERE won = " + false)){

            ArrayList<String> winners = new ArrayList<>();

            while (result.next()){

                winners.add(result.getString(1));

            }

            return winners;

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Error in getting tombola's participant.");
            e.printStackTrace();

        }

        return new ArrayList<>();
    }

    public static Player getTombolaWinner(int id){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT winner FROM " + database + ".`" + tombolaWinTable + "` WHERE id = " + id)){

            result.next();

            return Bukkit.getPlayer(result.getString(0));

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Error in getting tombola's participant.");
            e.printStackTrace();

        }

        return null;
    }

    public static boolean isTombolaWon(int id){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT won FROM " + database + ".`" + tombolaWinTable + "` WHERE id = " + id)){

            result.next();

            return result.getBoolean(0);

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Error in getting tombola's state.");
            e.printStackTrace();

        }

        return false;

    }

    public static int getLastTombolaWinId(){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT max(id) FROM " + database + ".`" + tombolaWinTable + "`")){

            result.next();

            return result.getInt(0);

        } catch (Exception e) {

            Bukkit.getLogger().log(Level.SEVERE, "Error in getting tombola's participant.");
            e.printStackTrace();

        }

        return 0;

    }

    static void init(){

        Configuration config = plugin.getConfig();
        database = config.getString("database");
        url = "jdbc:mysql://" + config.getString("host") + ":" + config.getString("port") + "/" + database;
        login = config.getString("login");
        password = config.getString("password");
        playersBankTable = config.getString("playersBankTable");
        entBankTable = config.getString("entBankTable");
        tombolaTable = config.getString("tombolaTable");
        tombolaWinTable = config.getString("tombolaWinTable");

        try(Connection connection = DriverManager.getConnection(url, login, password)){

            try(Statement state = connection.createStatement()){

                state.executeQuery("SELECT min(walletFunds) FROM " + database + ".`" + playersBankTable + "`");

            } catch (MySQLSyntaxErrorException e){

                Statement state = connection.createStatement();

                state.executeUpdate("CREATE TABLE " + database + ".`" + playersBankTable + "` (" +
                        "player TINYTEXT," +
                        "lastPseudo TINYTEXT," +
                        "walletFunds DOUBLE," +
                        "bankFunds DOUBLE" +
                        ") ENGINE = INNODB");

                state.close();
                Bukkit.getLogger().log(Level.INFO, "EconomyAPI: " + playersBankTable + " table created.");
            }

            Bukkit.getLogger().log(Level.INFO, "EconomyAPI: " + playersBankTable + " table works.");

        } catch (SQLException e) {

            Bukkit.getLogger().log(Level.SEVERE, "EconomyAPI: Error in checking " + playersBankTable + " table.");
            e.printStackTrace();

        }

        try(Connection connection = DriverManager.getConnection(url, login, password)){

            try(Statement state = connection.createStatement()){

                state.executeQuery("SELECT min(bankFunds) FROM " + database + ".`" + entBankTable + "`");

            } catch (MySQLSyntaxErrorException e){

                Statement state = connection.createStatement();

                state.executeUpdate("CREATE TABLE " + database + ".`" + entBankTable + "` (" +
                        "name TINYTEXT," +
                        "bankFunds DOUBLE" +
                        ") ENGINE = INNODB");

                state.close();
                Bukkit.getLogger().log(Level.INFO, "EconomyAPI: " + entBankTable + " table created.");
            }

            Bukkit.getLogger().log(Level.INFO, "EconomyAPI: " + entBankTable + " table works.");

        } catch (SQLException e) {

            Bukkit.getLogger().log(Level.SEVERE, "EconomyAPI: Error in checking " + entBankTable + " table.");
            e.printStackTrace();

        }

        try(Connection connection = DriverManager.getConnection(url, login, password)){

            try(Statement state = connection.createStatement()){

                state.executeQuery("SELECT player FROM " + database + ".`" + tombolaTable + "`");

            } catch (MySQLSyntaxErrorException e){

                Statement state = connection.createStatement();

                state.executeUpdate("CREATE TABLE " + database + ".`" + tombolaTable + "` (" +
                        "player TINYTEXT" +
                        ") ENGINE = INNODB");

                state.close();
                Bukkit.getLogger().log(Level.INFO, "EconomyAPI: " + tombolaTable + " table created.");
            }

            Bukkit.getLogger().log(Level.INFO, "EconomyAPI: " + tombolaTable + " table works.");

        } catch (SQLException e) {

            Bukkit.getLogger().log(Level.SEVERE, "EconomyAPI: Error in checking " + tombolaTable + " table.");
            e.printStackTrace();

        }

        try(Connection connection = DriverManager.getConnection(url, login, password)){

            try(Statement state = connection.createStatement()){

                state.executeQuery("SELECT min(id) FROM " + database + ".`" + tombolaWinTable + "`");

            } catch (MySQLSyntaxErrorException e){

                Statement state = connection.createStatement();

                state.executeUpdate("CREATE TABLE " + database + ".`" + tombolaWinTable + "` (" +
                        "id INT," +
                        "winner TINYTEXT," +
                        "funds DOUBLE" +
                        ") ENGINE = INNODB");

                state.close();
                Bukkit.getLogger().log(Level.INFO, "EconomyAPI: " + tombolaWinTable + " table created.");
            }

            Bukkit.getLogger().log(Level.INFO, "EconomyAPI: " + tombolaWinTable + " table works.");

        } catch (SQLException e) {

            Bukkit.getLogger().log(Level.SEVERE, "EconomyAPI: Error in checking " + tombolaWinTable + " table.");
            e.printStackTrace();

        }
    }

    static void reset(){

        try(Connection connection = DriverManager.getConnection(url, login, password);
            Statement state = connection.createStatement()){

            state.executeUpdate("DELETE FROM " + database + ".`" + playersBankTable + "`");

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    private static String patch(String str){

        return str.replaceAll("'", "''").replaceAll("\"", "\"\"");

    }
}
