package fr.paragoumba.minediversity.economyapi.commands;

import fr.paragoumba.minediversity.economyapi.events.Why;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class EventCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("event")) {
                int money = Why.getTicket(p);
                if (args.length == 0) {
                    p.sendMessage("§fVous avez §e" + money + "§f.");
                }

                Player pl;
                Iterator var8;
                int PLmoney;
                if (args.length == 1) {
                    var8 = Bukkit.getOnlinePlayers().iterator();

                    while (var8.hasNext()) {
                        pl = (Player) var8.next();
                        if (args[0].equalsIgnoreCase(pl.getName())) {
                            PLmoney = Why.getTicket(p);
                            p.sendMessage("§fLe joueur §4" + pl.getName() + "§f a §e" + PLmoney + "§f.");
                        }
                    }
                }

                if (args.length == 2) {
                    p.sendMessage("§4SYNTAXE ERREUR");
                }

                if (args.length == 3) {
                    var8 = Bukkit.getOnlinePlayers().iterator();

                    while (var8.hasNext()) {
                        pl = (Player) var8.next();
                        if (args[0].equalsIgnoreCase(pl.getName())) {
                            if (args[1].equalsIgnoreCase("set")) {
                                pl.sendMessage("§fDieu a fixé votre compte de tickets a §6" + args[2] + "£.");
                                PLmoney = Integer.valueOf(args[2]).intValue();
                                Why.removeTicket(pl, Why.getTicket(pl));
                                Why.addMoney(pl, PLmoney);
                            }

                            if (args[1].equalsIgnoreCase("add")) {
                                pl.sendMessage("§fDieu vous a ajouté §6" + args[2] + "£ §fsur votre compte de tickets.");
                                PLmoney = Integer.valueOf(args[2]).intValue();
                                Why.addTicket(pl, PLmoney);
                            }

                            if (args[1].equalsIgnoreCase("remove")) {
                                pl.sendMessage("§fDieu vous a retiré §6" + args[2] + "£ §fsur votre compte de tickets.");
                                PLmoney = Integer.valueOf(args[2]).intValue();
                                Why.removeTicket(pl, PLmoney);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
