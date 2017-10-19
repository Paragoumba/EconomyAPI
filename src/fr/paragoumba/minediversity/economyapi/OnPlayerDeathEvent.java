package fr.paragoumba.minediversity.economyapi;

import fr.paragoumba.minediversity.economyapi.events.Why;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player p = event.getEntity();

        p.sendMessage("§fVous avez §6perdu §fl'argent de votre porte monnaie : §6" + Why.getMoney(p) + "§f.");
        EconomyAPI.removeMoney(p, Why.getMoney(p));

    }
}
