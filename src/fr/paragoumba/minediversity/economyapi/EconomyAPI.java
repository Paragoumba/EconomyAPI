package fr.paragoumba.minediversity.economyapi;

import fr.paragoumba.minediversity.economyapi.commands.EventCommand;
import fr.paragoumba.minediversity.economyapi.commands.MoneyCommand;
import fr.paragoumba.minediversity.economyapi.commands.TombolaCommand;
import fr.paragoumba.minediversity.economyapi.events.Why;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class EconomyAPI extends JavaPlugin implements Listener {

    Random r = new Random();
    boolean tombolafinish = false;
    private int timer;
    private int task;

    public EconomyAPI(){}

    public void onEnable() {

        System.out.println("API démarrer");
        File configFile = new File(this.getDataFolder(), "config.yml");

        if (!configFile.exists()) {

            this.getConfig().options().copyDefaults(true);
            this.saveConfig();

        }

        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(this, this);
        pm.registerEvents(new Why(this), this);

        this.getCommand("tombola").setExecutor(new TombolaCommand());
        this.getCommand("money").setExecutor(new MoneyCommand());
        this.getCommand("event").setExecutor(new EventCommand());

        this.boucle(-1);
    }

    @Override
    public void onDisable() {
        System.out.println("API stopper");
    }

    public void boucle(int i) {
        this.timer = i;
        this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {

                Date d = new Date();

                EconomyAPI.this.getConfig().set("day", d.getDate());
                EconomyAPI.this.getConfig().set("month", d.getMonth() + 1);
                EconomyAPI.this.getConfig().set("year", d.getYear() + 1900);

                if (d.getHours() == 22 && d.getMinutes() == 10) {

                    EconomyAPI.this.tombolafinish = false;

                }

                Iterator var3 = Bukkit.getOnlinePlayers().iterator();

                while(var3.hasNext()) {

                    Player p = (Player)var3.next();

                    if (!EconomyAPI.this.tombolafinish && EconomyAPI.this.timer == 0 && !EconomyAPI.this.getConfig().getStringList("tombola participant").contains(String.valueOf(p.getUniqueId()))) {

                        p.sendMessage("-[]---§6 Tombola §f---[]-");
                        p.sendMessage("Pour miser §650£ §fet peux-être §6gagner §fla mise totale");
                        p.sendMessage("Il vous faut effectuer un §6/tombola§f.");
                        p.sendMessage("-[]-------------------------------------------------------[]-");

                    }

                    String UUID = String.valueOf(p.getUniqueId());
                    ConfigurationSection pc = EconomyAPI.this.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);

                    if (pc.getInt("argent du joueur") < EconomyAPI.this.getConfig().getInt("nombre d'argent minimum")){

                        pc.set("argent du joueur", EconomyAPI.this.getConfig().getInt("nombre d'argent minimum"));
                        EconomyAPI.this.saveConfig();

                    }

                    int Jday = pc.getInt("day");
                    int JMonth = pc.getInt("month");
                    int JYear = pc.getInt("year");
                    int Jmoney = pc.getInt("argent du joueur");
                    int Aday = EconomyAPI.this.getConfig().getInt("day");
                    int AMonth = EconomyAPI.this.getConfig().getInt("month");
                    int AYear = EconomyAPI.this.getConfig().getInt("year");
                    int pourcentMoney;

                    if (Jday != Aday) {

                        p.sendMessage("Votre solde a subi une taxe de §62%");
                        pourcentMoney = (int)((double)Jmoney * 0.98D);
                        pc.set("argent du joueur en banque", Jmoney - pourcentMoney);

                    } else if (JMonth != AMonth) {

                        p.sendMessage("Votre solde a subi une taxe de §62%");
                        pourcentMoney = (int)((double)Jmoney * 0.98D);
                        pc.set("argent du joueur", Jmoney - pourcentMoney);

                    } else if (JYear != AYear) {

                        p.sendMessage("Votre solde a subi une taxe de §62%");
                        pourcentMoney = (int)((double)Jmoney * 0.98D);
                        pc.set("argent du joueur", Jmoney - pourcentMoney);

                    }

                    pc.set("day", d.getDate());
                    pc.set("month", d.getMonth() + 1);
                    pc.set("year", d.getYear() + 1900);

                }

                if (d.getHours() == 22 && !EconomyAPI.this.tombolafinish) {

                    EconomyAPI.this.tombolafinish = true;
                    int Size = EconomyAPI.this.getConfig().getStringList("tombola participant").size();
                    int Winner = EconomyAPI.this.r.nextInt(Size);

                    if (Winner == 0) {

                        Winner = 1;

                    }

                    Player px;

                    for(Iterator var17 = Bukkit.getOnlinePlayers().iterator(); var17.hasNext(); px.sendMessage("§fLa tombola est terminé mais la suivante redémarre dans §610 minute.")) {

                        px = (Player)var17.next();

                        if (EconomyAPI.this.getConfig().getStringList("tombola participant").contains(String.valueOf(px.getUniqueId()))) {

                            if (EconomyAPI.this.getConfig().getStringList("tombola participant").get(Winner) == String.valueOf(px.getUniqueId())) {

                                px.sendMessage("§fBravo vous avez ganger la tombola d'aujourd'hui");
                                px.sendMessage("§fsolde gagné :" + 80 % EconomyAPI.this.getConfig().getInt("mise a la tombola"));

                            } else {

                                px.sendMessage("§fVous avez perdu la tombola mais ce n'est pas grave Vous pouvez réésayer !");

                            }
                        }
                    }
                }

                if (EconomyAPI.this.timer == 0) {

                    EconomyAPI.this.timer = 18000;

                }

            }
        }, 2L, 2L);
    }

    public static void addMoney(Player p, int amount){

        Why.addMoney(p, amount);

    }

    public static void removeMoney(Player p, int amount){

        Why.removeMoney(p, amount);

    }

    public static void getMoney(Player p, int amount){

        Why.getMoney(p);

    }

    public static void addTicket(Player p, int amount){

        Why.addTicket(p, amount);

    }

    public static void removeTicket(Player p, int amount){

        Why.removeTicket(p, amount);

    }

    public static void getTicket(Player p, int amount){

        Why.getTicket(p);

    }
}
