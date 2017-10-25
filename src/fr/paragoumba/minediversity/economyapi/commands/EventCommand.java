package fr.paragoumba.minediversity.economyapi.commands;

import fr.paragoumba.minediversity.economyapi.Database;
import fr.paragoumba.minediversity.economyapi.events.Why;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

/**
 * @deprecated
 */

@Deprecated
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

                Player player;
                Iterator var8;
                int PLmoney;

                if (args.length == 1) {

                    var8 = Bukkit.getOnlinePlayers().iterator();

                    while (var8.hasNext()) {

                        player = (Player) var8.next();

                        if (args[0].equalsIgnoreCase(player.getName())) {

                            PLmoney = Why.getTicket(p);
                            p.sendMessage("§4" + player.getName() + "§f a §e" + PLmoney + "§f£.");

                        }
                    }
                }

                if (args.length == 2) {

                    p.sendMessage("§4SYNTAXE ERREUR");

                }

                if (args.length == 3) {

                    var8 = Bukkit.getOnlinePlayers().iterator();

                    while (var8.hasNext()) {

                        player = (Player) var8.next();

                        if (args[0].equalsIgnoreCase(player.getName())) {

                            if (args[1].equalsIgnoreCase("set")) {

                                player.sendMessage("§fDieu a fixé votre compte de tickets a §6" + args[2] + "£.");
                                PLmoney = Integer.valueOf(args[2]).intValue();
                                Why.removeTicket(player, Why.getTicket(player));
                                Database.addBankFunds(player, PLmoney);

                            }

                            if (args[1].equalsIgnoreCase("add")) {

                                player.sendMessage("§fDieu vous a ajouté §6" + args[2] + "£ §fsur votre compte de tickets.");
                                PLmoney = Integer.valueOf(args[2]).intValue();
                                Why.addTicket(player, PLmoney);

                            }

                            if (args[1].equalsIgnoreCase("remove")) {

                                player.sendMessage("§fDieu vous a retiré §6" + args[2] + "£ §fsur votre compte de tickets.");
                                PLmoney = Integer.valueOf(args[2]).intValue();
                                Why.removeTicket(player, PLmoney);

                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
