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
     * Permet de récupèrer le possesseur du compte.
     * @return Le joueur qui possède le compte.
     */
    @Override
    public String getName(){

        return owner.getName();

    }

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
     * Permet de récupèrer l'argent présent sur le compte bancaire du joueur.
     * @return L'argent sur le compte bancaire du joueur.
     */
    @Override
    public double getBankFunds(){

        return Database.getPlayerBankFunds(owner);

    }

    /**
     * Permet de définir l'argent présent sur le compte bancaire du joueur.
     * @param funds L'argent à mettre sur le compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    @Override
    public boolean setBankFunds(double funds){

        return Database.setPlayerBankFunds(owner, funds);

    }

    /**
     * Permet d'ajouter de l'argent sur le compte bancaire du joueur.
     * @param funds L'argent à ajouter au compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    @Override
    public boolean addBankFunds(double funds){

        return Database.addPlayerBankFunds(owner, funds);

    }

    /**
     * Permet de retirer de l'argent du compte bancaire du joueur.
     * @param funds L'argent à retirer du compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    @Override
    public boolean subBankFunds(double funds){

        return Database.subPlayerBankFunds(owner, funds);

    }
}
