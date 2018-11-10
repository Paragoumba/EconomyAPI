package fr.paragoumba.minediversity.economyapi.events;

import fr.paragoumba.minediversity.economyapi.Database;
import fr.paragoumba.minediversity.economyapi.objects.Tombola;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static fr.paragoumba.minediversity.economyapi.EconomyAPI.mainColor;
import static fr.paragoumba.minediversity.economyapi.EconomyAPI.moneySymbol;

/**
 * Created by Paragoumba on 23/10/2017.
 */

public class PlayerJoinEventHandler implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (!Database.playerHasAccount(player)){

            Database.createAccount(player);

        }

        if (Database.getTombolaWinners().contains(player.getUniqueId().toString())){

            Tombola tombola = new Tombola(Database.getTombolaId(player));
            double playerFunds = tombola.getFunds() * 0.8;

            Database.addPlayerFunds(player, playerFunds);
            Database.addEntFunds("tombola", tombola.getFunds() * 0.2);
            Database.setTombolaWon();
            player.sendMessage("Vous avez gagné la tombola ! Vous gagnez " + mainColor + String.format("%.1f", playerFunds) + moneySymbol + ChatColor.RESET + " !");

        }
    }
}
