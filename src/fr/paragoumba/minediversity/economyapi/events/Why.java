package fr.paragoumba.minediversity.economyapi.events;

import java.util.Date;
import java.util.List;

import fr.paragoumba.minediversity.economyapi.EconomyAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Why implements Listener {

    public static EconomyAPI pl2;
    public EconomyAPI pl;

    public Why(EconomyAPI pl) {
        this.pl = pl;
        pl2 = pl;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if (!this.pl.getConfig().getConfigurationSection("liste des joueurs").contains(String.valueOf(p.getUniqueId()))) {

            String UUID = String.valueOf(p.getUniqueId());
            String Name = p.getName();

            this.pl.getConfig().getConfigurationSection("liste des joueurs").createSection(UUID);

            ConfigurationSection pc = this.pl.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
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
            this.pl.saveConfig();

        }
    }

    public static void addMoney(Player p, int amount) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = pl2.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
        int actualmoney = pc.getInt("argent du joueur");

        pc.set("argent du joueur", actualmoney + amount);
        pl2.saveConfig();

    }

    public static void removeMoney(Player p, int amount) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = pl2.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
        int actualmoney = pc.getInt("argent du joueur");

        pc.set("argent du joueur", actualmoney - amount);
        pl2.saveConfig();

    }

    public static int getMoney(Player p) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = pl2.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
        int money = pc.getInt("argent du joueur");

        return money;

    }

    public static void addTicket(Player p, int amount) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = pl2.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
        int actualmoney = pc.getInt("ticket event");

        pc.set("ticket event", actualmoney + amount);
        pl2.saveConfig();

    }

    public static void removeTicket(Player p, int amount) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = pl2.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
        int actualmoney = pc.getInt("ticket event");

        pc.set("ticket event", actualmoney - amount);
        pl2.saveConfig();

    }

    public static int getTicket(Player p) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = pl2.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
        int money = pc.getInt("ticket event");

        return money;

    }

    public static int getBank(Player p) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = pl2.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);
        int money = pc.getInt("argent du joueur en banque");

        return money;

    }

    public static void addBank(Player p, int Money) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = pl2.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);

        pc.set("argent du joueur en banque", getBank(p) + Money);

    }

    public static void removeBank(Player p, int Money) {

        String UUID = String.valueOf(p.getUniqueId());
        ConfigurationSection pc = pl2.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);

        pc.set("argent du joueur en banque", getBank(p) - Money);

    }

    public static String getSymbole() {

        return pl2.getConfig().getString("symbole de l'argent");

    }

    public static boolean PlayerIsOnTombola(Player p) {

        return !pl2.getConfig().getStringList("tombola participant").contains(String.valueOf(p.getUniqueId()));

    }

    public static void addToTombola(Player p) {

        List<String> participant = pl2.getConfig().getStringList("tombola participant");

        participant.add(String.valueOf(p.getUniqueId()));
        pl2.getConfig().set("tombola participant", participant);
        pl2.saveConfig();

    }
}
