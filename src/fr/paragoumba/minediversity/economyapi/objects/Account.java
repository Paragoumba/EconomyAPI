package fr.paragoumba.minediversity.economyapi.objects;

import fr.paragoumba.minediversity.economyapi.Database;
import org.bukkit.entity.Player;

/**
 * Created by Paragoumba on 23/10/2017.
 */

public class Account {

    public Account(int id, Player owner, double funds){

        this.id = id;
        this.owner = owner;
        this.funds = funds;

    }

    private int id;
    private Player owner;
    private double funds;

    int getId(){

        return id;

    }

    Player getOwner(){

        return owner;

    }

    String getLastPseudo(){

        return Database.getLastPseudo(id);

    }

    double getFunds(){

        return Database.getFunds(id);

    }
}
