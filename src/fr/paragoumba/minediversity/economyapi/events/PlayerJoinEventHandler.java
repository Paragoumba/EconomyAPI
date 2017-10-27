package fr.paragoumba.minediversity.economyapi.events;

import fr.paragoumba.minediversity.economyapi.Database;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Paragoumba on 23/10/2017.
 */

public class PlayerJoinEventHandler implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (!Database.playerHasAccount(player)){

            Database.createAccount(player);

        }
    }
}
