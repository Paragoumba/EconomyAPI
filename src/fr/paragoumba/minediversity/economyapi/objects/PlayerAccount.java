package fr.paragoumba.minediversity.economyapi.objects;

import fr.paragoumba.minediversity.economyapi.Database;
import org.bukkit.entity.Player;

/**
 * Contient toutes les informations concernant le porte-monnaie et le compte en banque du joueur.
 */

public class PlayerAccount implements Account {

    public PlayerAccount(Player owner){

        this.owner = owner;

    }

    private Player owner;

    /**
     * Permet de récupèrer le nom du possesseur du compte.
     * @return Le nom du joueur qui possède le compte.
     */
    @Override
    public String getName(){

        return owner.getName();

    }

    /**
     * Permet de récupèrer le possesseur du compte.
     * @return Le joueur qui possède le compte.
     */
    public Player getOwner(){

        return owner;

    }

    /**
     * Permet de récupèrer le dernier pseudo connu du possesseur du compte.
     * @return Le dernier pseudo connu du possesseur du compte.
     */
    public String getLastPseudo(){

        return Database.getLastPseudo(owner);

    }

    /**
     * Permet de récupèrer l'argent présent dans le porte-monnaie du joueur.
     * @return L'argent dans le porte-monnaie du joueur.
     */
    public double getWalletFunds(){

        return Database.getWalletFunds(owner);

    }

    /**
     * Permet de définir l'argent présent dans le porte-monnaie du joueur.
     * @param amount L'argent à mettre dans le porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public boolean setWalletFunds(double amount){

        return Database.setWalletFunds(owner, amount);

    }

    /**
     * Permet d'ajouter de l'argent dans le porte-monnaie du joueur.
     * @param amount L'argent à ajouter au porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public boolean addWalletFunds(double amount){

        return Database.addWalletFunds(owner, amount);

    }

    /**
     * Permet de retirer de l'argent dans le porte-monnaie du joueur.
     * @param amount L'argent à retirer du porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public boolean subWalletFunds(double amount){

        return Database.subWalletFunds(owner, amount);

    }

    /**
     * Permet de récupèrer l'argent présent sur le compte bancaire du joueur.
     * @return L'argent sur le compte bancaire du joueur.
     */
    @Override
    public double getBankFunds(){

        return Database.getPlayerBankFunds(owner);

    }

    /**
     * Permet de définir l'argent présent sur le compte bancaire du joueur.
     * @param amount L'argent à mettre sur le compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    @Override
    public boolean setBankFunds(double amount){

        return Database.setPlayerBankFunds(owner, amount);

    }

    /**
     * Permet d'ajouter de l'argent sur le compte bancaire du joueur.
     * @param amount L'argent à ajouter au compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    @Override
    public boolean addBankFunds(double amount){

        return Database.addPlayerBankFunds(owner, amount);

    }

    /**
     * Permet de retirer de l'argent du compte bancaire du joueur.
     * @param amount L'argent à retirer du compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    @Override
    public boolean subBankFunds(double amount){

        return Database.subPlayerBankFunds(owner, amount);

    }
}
