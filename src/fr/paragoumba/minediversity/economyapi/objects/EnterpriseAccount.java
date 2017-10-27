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

    @Override
    public String getName() {

        return name;

    }

    @Override
    public double getBankFunds() {

        return Database.getEntBankFunds(name);

    }

    @Override
    public boolean setBankFunds(double amount) {

        return Database.setEntBankFunds(name, amount);

    }

    @Override
    public boolean addBankFunds(double amount) {

        return Database.addEntBankFunds(name, amount);

    }

    @Override
    public boolean subBankFunds(double amount) {

        return Database.subEntBankFunds(name, amount);

    }
}
