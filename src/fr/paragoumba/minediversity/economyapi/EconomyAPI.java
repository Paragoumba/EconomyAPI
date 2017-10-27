package fr.paragoumba.minediversity.economyapi;

import fr.paragoumba.minediversity.economyapi.commands.APIReloadCommand;
import fr.paragoumba.minediversity.economyapi.commands.MoneyCommand;
import fr.paragoumba.minediversity.economyapi.commands.TombolaCommand;
import fr.paragoumba.minediversity.economyapi.events.PlayerDeathEventHandler;
import fr.paragoumba.minediversity.economyapi.events.PlayerJoinEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class EconomyAPI extends JavaPlugin implements Listener {

    private Random r = new Random();
    private boolean tombolafinish = false;
    private int timer;

    static EconomyAPI plugin;
    public static ChatColor mainColor;
    public static ChatColor errorColor;
    public static String moneySymbol;
    public static double tombolaPrice;
    public static HashMap<String, String>commandsArgs = new HashMap<>();

    public void onEnable() {

        plugin = this;
        File configFile = new File(this.getDataFolder(), "config.yml");

        if (!configFile.exists()) {

            getConfig().options().copyDefaults(true);
            saveConfig();

        }

        init();

        //Events
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinEventHandler(), this);
        pm.registerEvents(new PlayerDeathEventHandler(), this);

        //Commands
        getCommand("tombola").setExecutor(new TombolaCommand());
        getCommand("money").setExecutor(new MoneyCommand());
        getCommand("apireload").setExecutor(new APIReloadCommand());

        commandsArgs.put("add", "/money add <player> <amount>");
        commandsArgs.put("sub", "/money sub <player> <amount>");
        commandsArgs.put("set", "/money set <player> <amount>");
        commandsArgs.put("give", "/money give <player> <amount>");

        tombolaInfoMessageTask();

        System.out.println("Initialization done.");

    }

    @Override
    public void onDisable() {

        System.out.println("Disabling EconomyAPI");

    }

    public static void init(){

        Database.init();

        Configuration config = plugin.getConfig();
        mainColor = ChatColor.valueOf(config.getString("mainColor"));
        errorColor = ChatColor.valueOf(config.getString("errorColor"));
        moneySymbol = config.getString("moneySymbol");
        tombolaPrice = config.getDouble("tombolaPrice");

    }

    private void tombolaInfoMessageTask(){

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {

            Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

            for (Player player : onlinePlayers) {

                if (!Database.getTombolaParticipant(player)) {

                    player.sendMessage("Veux-tu participer à la tombola ? Pour plus d'infos, fais /tombola !");

                }
            }

        }, 72, 72000);
    }

    @Deprecated
    public void boucle(int i) {

        this.timer = i;
        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {

            Date d = new Date();

            EconomyAPI.this.getConfig().set("day", d.getDate());
            EconomyAPI.this.getConfig().set("month", d.getMonth() + 1);
            EconomyAPI.this.getConfig().set("year", d.getYear() + 1900);

            if (d.getHours() == 22 && d.getMinutes() == 10) {

                EconomyAPI.this.tombolafinish = false;

            }

            Iterator var3 = Bukkit.getOnlinePlayers().iterator();

            while (var3.hasNext()) {

                Player p = (Player) var3.next();

                if (!EconomyAPI.this.tombolafinish && EconomyAPI.this.timer == 0 && !EconomyAPI.this.getConfig().getStringList("tombola participant").contains(String.valueOf(p.getUniqueId()))) {

                    p.sendMessage("-[]---§6 Tombola §f---[]-");
                    p.sendMessage("Pour miser §650£ §fet peux-être §6gagner §fla mise totale");
                    p.sendMessage("Il vous faut effectuer un §6/tombola§f.");
                    p.sendMessage("-[]-------------------------------------------------------[]-");

                }

                String UUID = String.valueOf(p.getUniqueId());
                ConfigurationSection pc = EconomyAPI.this.getConfig().getConfigurationSection("liste des joueurs").getConfigurationSection(UUID);

                if (pc.getInt("argent du joueur") < EconomyAPI.this.getConfig().getInt("nombre d'argent minimum")) {

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
                    pourcentMoney = (int) ((double) Jmoney * 0.98D);
                    pc.set("argent du joueur en banque", Jmoney - pourcentMoney);

                } else if (JMonth != AMonth) {

                    p.sendMessage("Votre solde a subi une taxe de §62%");
                    pourcentMoney = (int) ((double) Jmoney * 0.98D);
                    pc.set("argent du joueur", Jmoney - pourcentMoney);

                } else if (JYear != AYear) {

                    p.sendMessage("Votre solde a subi une taxe de §62%");
                    pourcentMoney = (int) ((double) Jmoney * 0.98D);
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

                for (Iterator var17 = Bukkit.getOnlinePlayers().iterator(); var17.hasNext(); px.sendMessage("§fLa tombola est terminé mais la suivante redémarre dans §610 minute.")) {

                    px = (Player) var17.next();

                    if (EconomyAPI.this.getConfig().getStringList("tombola participant").contains(String.valueOf(px.getUniqueId()))) {

                        if (EconomyAPI.this.getConfig().getStringList("tombola participant").get(Winner) == String.valueOf(px.getUniqueId())) {

                            px.sendMessage("§fBravo vous avez ganger la tombola d'aujourd'hui");
                            px.sendMessage("§fsolde gagné :" + 80 % EconomyAPI.this.getConfig().getInt("mise a la tombola"));

                        } else {

                            px.sendMessage("§fVous avez perdu la tombola mais ce n'est pas grave. Vous pouvez réessayer !");

                        }
                    }
                }
            }

            if (EconomyAPI.this.timer == 0) {

                EconomyAPI.this.timer = 18000;

            }

        }, 2L, 2L);
    }
}
