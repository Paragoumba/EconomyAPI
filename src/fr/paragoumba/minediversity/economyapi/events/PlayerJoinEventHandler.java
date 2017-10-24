package fr.paragoumba.minediversity.economyapi.events;

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
    public void playerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if (!plugin.getConfig().getConfigurationSection("liste des joueurs").contains(String.valueOf(p.getUniqueId()))) {

            String UUID = String.valueOf(p.getUniqueId());
            String Name = p.getName();
            plugin.getConfig().getConfigurationSection("liste des joueurs").createSection(UUID);
            ConfigurationSection pc = plugin.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
            Date d = new Date();
            pc.createSection("nom du joueur");
            pc.set("nom du joueur", Name);
            pc.createSection("argent du joueur en banque");
            pc.set("argent du joueur en banque", Integer.valueOf(20));
            pc.createSection("argent du joueur");
            pc.set("argent du joueur", Integer.valueOf(20));
            pc.createSection("ticket event");
            pc.set("ticket event", Integer.valueOf(0));
            pc.createSection("day");
            pc.set("day", d.getDate());
            pc.createSection("month");
            pc.set("month", d.getMonth() + 1);
            pc.createSection("year");
            pc.set("year", d.getYear() + 1900);
            plugin.saveConfig();

        }
    }
}
