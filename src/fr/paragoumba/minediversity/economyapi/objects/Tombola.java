package fr.paragoumba.minediversity.economyapi.objects;

import fr.paragoumba.minediversity.economyapi.Database;
import org.bukkit.entity.Player;

/**
 * Created by Paragoumba on 03/11/2017.
 */

public class Tombola {

    public Tombola(int id){

        this.id = id;

    }

    private int id;

    public Player getWinner(){

        return Database.getTombolaWinner(id);

    }

    public double getFunds(){

        return Database.getTombolaFunds(id);

    }

    public boolean addFunds(double amount){

        return Database.addTombolaFunds(id, amount);

    }

    public boolean setFunds(double amount){

        return Database.setTombolaFunds(id, amount);

    }

    public boolean subFunds(double amount){

        return Database.subTombolaFunds(id, amount);

    }

    public boolean wasWon(){

        return Database.isTombolaWon(id);

    }
}
