package fr.paragoumba.minediversity.economyapi.events;

import fr.paragoumba.minediversity.economyapi.objects.PlayerAccount;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Paragoumba on 24/10/2017.
 */

public class PlayerDeathEventHandler implements Listener {

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {

        Player player = event.getEntity();
        PlayerAccount account = new PlayerAccount(player);

        player.sendMessage("§fVous avez §6perdu §fl'argent de votre porte monnaie : §6" + account.getWalletFunds() +"§f.");
        account.setWalletFunds(0);
    }
}
