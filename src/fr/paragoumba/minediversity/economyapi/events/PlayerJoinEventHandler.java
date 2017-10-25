package fr.paragoumba.minediversity.economyapi.events;

import fr.paragoumba.minediversity.economyapi.Database;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;

import static fr.paragoumba.minediversity.economyapi.EconomyAPI.plugin;

/**
 * Created by Paragoumba on 23/10/2017.
 */

public class PlayerJoinEventHandler implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (!Database.getTombolaParticipant(player)) {

            player.sendMessage("Veux-tu participer à la tombola ? Pour plus d'infos, fais /tombola infos !");

        }
    }
}
