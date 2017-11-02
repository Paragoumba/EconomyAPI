package fr.paragoumba.minediversity.economyapi.commands;

import fr.paragoumba.minediversity.economyapi.Database;
import fr.paragoumba.minediversity.economyapi.EconomyAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static fr.paragoumba.minediversity.economyapi.EconomyAPI.*;

public class TombolaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {

            Player player = (Player)commandSender;

            if (strings.length > 0) {

                switch (strings[0]) {

                    case "play":

                        if (!Database.getTombolaParticipant(player)) {

                            if (Database.getPlayerBankFunds(player) >= tombolaPrice) {

                                Database.subPlayerBankFunds(player, tombolaPrice);
                                Database.addTombolaParticipant(player);
                                player.sendMessage("Vous participez maintenant à tombola.");
                                player.sendMessage(mainColor + "" + tombolaPrice + moneySymbol + ChatColor.RESET + " vous ont été retirés.");

                            } else {

                                player.sendMessage("Vous n'avez pas assez d'argent sur votre compte bancaire pour participer. Il vous faut " + mainColor + "" + tombolaPrice + ChatColor.RESET + "£");

                            }

                        } else {

                            player.sendMessage("Vous participez déjà à la tombola.");

                        }

                        return true;
                }
            }

            player.sendMessage("-§6[§f|§6]§f-----------------§6Tombola's Infos§f-----------------§6[§f|§6]§f-\n" +
                    "/tombola play : Vous incrit à la tombola.\n" +
                    "L'inscription à la tombola coûte " + tombolaPrice + moneySymbol + ".\n" +
                    "Une tombola est organisée tous les jours et le tirage au sort se fait à " + mainColor + "22h" + ChatColor.RESET + ".");

            player.sendMessage("-§6[§f|§6]§f-----------------§6Tombola's Infos§f-----------------§6[§f|§6]§f-");
            return true;

        } else if (commandSender instanceof ConsoleCommandSender){

            if (strings.length > 0 && strings[0].equalsIgnoreCase("launch")){

                List<Player> participants = Database.getTombolaParticipants();
                Player winner = participants.get((int) Math.round(Math.random() * participants.size()));

                Database.setTombolaWinner(winner);

                if (winner.isOnline()){

                    double tombolaFunds = Database.getTombolaFunds();

                    Database.addPlayerBankFunds(winner, tombolaFunds * 0.8);
                    Database.addEntBankFunds("tombola", tombolaFunds * 0.2);

                }

                return true;
            }
        }

        commandSender.sendMessage("Only players can use this command. ¯\\_(ツ)_/¯");
        return true;
    }
}
