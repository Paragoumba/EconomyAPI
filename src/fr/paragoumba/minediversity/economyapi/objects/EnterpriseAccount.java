package fr.paragoumba.minediversity.economyapi.objects;

import fr.paragoumba.minediversity.economyapi.Database;

/**
 * Created by Paragoumba on 26/10/2017.
 */

public class EnterpriseAccount implements Account {

    public EnterpriseAccount(String name){

        this.name = name;

    }

    private String name;

    /**
     * Permet de récupèrer le nom de l'entrprise qui possède le compte.
     * @return Le nom de l'entrprise qui possède le compte.
     */
    @Override
    public String getName() {

        return name;

    }

    /**
     * Permet de récupèrer l'argent présent sur le compte bancaire de l'entreprise.
     * @return L'argent sur le compte bancaire de l'entreprise.
     */
    @Override
    public double getFunds() {

        return Database.getEntFunds(name);

    }

    /**
     * Permet de définir l'argent présent sur le compte bancaire de l'entreprise.
     * @param amount L'argent à mettre sur le compte bancaire de l'entreprise.
     * @return true si la transaction a réussi, sinon false.
     */
    @Override
    public boolean setFunds(double amount) {

        return Database.setEntFunds(name, amount);

    }

    /**
     * Permet d'ajouter de l'argent sur le compte bancaire de l'entreprise.
     * @param amount L'argent à ajouter au compte bancaire de l'entreprise.
     * @return true si la transaction a réussi, sinon false.
     */
    @Override
    public boolean addFunds(double amount) {

        return Database.addEntFunds(name, amount);

    }

    /**
     * Permet de retirer de l'argent du compte bancaire de l'entreprise.
     * @param amount L'argent à retirer du compte bancaire de l'entreprise.
     * @return true si la transaction a réussi, sinon false.
     */
    @Override
    public boolean subFunds(double amount) {

        return Database.subEntFunds(name, amount);

    }
}
