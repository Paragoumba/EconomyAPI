package fr.paragoumba.minediversity.economyapi.objects;

import fr.paragoumba.minediversity.economyapi.Database;
import org.bukkit.entity.Player;

/**
 * Cette classe contient toutes les informations concernant le porte-monnaie et le compte en banque du joueur.
 */

public class Account {

    public Account(Player owner){

        this.owner = owner;
        Database.createAccount(owner);

    }

    private Player owner;

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
     * Permet de récupèrer l'argent présent sur le compte bancaire du joueur.
     * @return L'argent sur le compte bancaire du joueur.
     */
    public double getBankFunds(){

        return Database.getBankFunds(owner);

    }

    /**
     * Permet de définir l'argent présent dans le porte-monnaie du joueur.
     * @param funds L'argent à mettre dans le porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public boolean setWalletFunds(double funds){

        return Database.setWalletFunds(owner, funds);

    }

    /**
     * Permet d'ajouter de l'argent dans le porte-monnaie du joueur.
     * @param funds L'argent à ajouter au porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public boolean addWalletFunds(double funds){

        return Database.addWalletFunds(owner, funds);

    }

    /**
     * Permet de retirer de l'argent dans le porte-monnaie du joueur.
     * @param funds L'argent à retirer du porte-monnaie du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public boolean subWalletFunds(double funds){

        return Database.subWalletFunds(owner, funds);

    }

    /**
     * Permet de définir l'argent présent sur le compte bancaire du joueur.
     * @param funds L'argent à mettre sur le compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public boolean setBankFunds(double funds){

        return Database.setBankFunds(owner, funds);

    }

    /**
     * Permet d'ajouter de l'argent sur le compte bancaire du joueur.
     * @param funds L'argent à ajouter au compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public boolean addBankFunds(double funds){

        return Database.addBankFunds(owner, funds);

    }

    /**
     * Permet de retirer de l'argent du compte bancaire du joueur.
     * @param funds L'argent à retirer du compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    public boolean subBankFunds(double funds){

        return Database.subBankFunds(owner, funds);

    }
}
