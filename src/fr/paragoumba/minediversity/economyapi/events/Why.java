package fr.paragoumba.minediversity.economyapi.events;

import fr.paragoumba.minediversity.economyapi.EconomyAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * @deprecated
 */

@Deprecated
public class Why implements Listener {

    public static EconomyAPI plugin;

    public Why(EconomyAPI plugin) {

        Why.plugin = plugin;

    }

    public static void addTicket(Player p, int amount) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = plugin.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
        int actualmoney = pc.getInt("ticket event");

        pc.set("ticket event", actualmoney + amount);
        plugin.saveConfig();

    }

    public static void removeTicket(Player p, int amount) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = plugin.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
        int actualmoney = pc.getInt("ticket event");

        pc.set("ticket event", actualmoney - amount);
        plugin.saveConfig();

    }

    public static int getTicket(Player p) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = plugin.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
        int money = pc.getInt("ticket event");

        return money;

    }
}
