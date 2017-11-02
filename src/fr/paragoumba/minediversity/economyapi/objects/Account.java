package fr.paragoumba.minediversity.economyapi.objects;

/**
 * Contient toutes les informations concernant un compte en banque.
 */

public interface Account {

    /**
     * Permet de récupèrer le possesseur du compte.
     * @return Le nom de l'entité à qui appartient le compte.
     */
    String getName();

    /**
     * Permet de récupèrer l'argent présent sur le compte bancaire du joueur.
     * @return L'argent sur le compte bancaire du joueur.
     */
    double getBankFunds();

    /**
     * Permet de définir l'argent présent sur le compte bancaire du joueur.
     * @param amount L'argent à mettre sur le compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    boolean setBankFunds(double amount);

    /**
     * Permet d'ajouter de l'argent sur le compte bancaire du joueur.
     * @param amount L'argent à ajouter au compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    boolean addBankFunds(double amount);

    /**
     * Permet de retirer de l'argent du compte bancaire du joueur.
     * @param amount L'argent à retirer du compte bancaire du joueur.
     * @return true si la transaction a réussi, sinon false.
     */
    boolean subBankFunds(double amount);
}
