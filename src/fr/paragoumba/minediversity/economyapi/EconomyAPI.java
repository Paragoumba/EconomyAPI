package fr.paragoumba.minediversity.economyapi;

import fr.paragoumba.minediversity.economyapi.commands.APIReloadCommand;
import fr.paragoumba.minediversity.economyapi.commands.MoneyCommand;
import fr.paragoumba.minediversity.economyapi.commands.TombolaCommand;
import fr.paragoumba.minediversity.economyapi.events.PlayerJoinEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class EconomyAPI extends JavaPlugin implements Listener {

    public static EconomyAPI plugin;
    public static ChatColor mainColor;
    public static ChatColor errorColor;
    public static String moneySymbol;
    public static double tombolaPrice;
    public static String accessErrorMessage;
    public static String amountErrorMessage;
    public static String playerErrorMessage;
    public static HashMap<String, String> commandsArgs = new HashMap<>();

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

        //Commands
        getCommand("tombola").setExecutor(new TombolaCommand());
        getCommand("money").setExecutor(new MoneyCommand());
        getCommand("apireload").setExecutor(new APIReloadCommand());

        commandsArgs.put("add", "/money add <joueur> <montant>");
        commandsArgs.put("sub", "/money sub <joueur> <montant>");
        commandsArgs.put("set", "/money set <joueur> <montant>");
        commandsArgs.put("give", "/money give <joueur> <montant>");
        commandsArgs.put("see", "/money see <joueur>");

        tombolaInfoMessageTask();

    }

    public static void init(){

        Database.init();

        Configuration config = plugin.getConfig();

        mainColor = ChatColor.valueOf(config.getString("mainColor"));
        errorColor = ChatColor.valueOf(config.getString("errorColor"));

        moneySymbol = config.getString("moneySymbol");

        tombolaPrice = config.getDouble("tombolaPrice");

        accessErrorMessage = config.getString("accessErrorMessage");
        amountErrorMessage = config.getString("amountErrorMessage");
        playerErrorMessage = config.getString("playerErrorMessage");

    }

    private void tombolaInfoMessageTask(){

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {

            for (Player player : Bukkit.getOnlinePlayers()) {

                if (!Database.getTombolaParticipant(player)) {

                    player.sendMessage("Veux-tu participer à la tombola ? Pour plus d'infos, fais /tombola !");

                }
            }

        }, 1200, 72000);
    }
}
