package fr.paragoumba.minediversity.economyapi.commands;

import fr.paragoumba.minediversity.economyapi.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;

import static fr.paragoumba.minediversity.economyapi.EconomyAPI.*;

public class MoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            switch (s){

                case "add":

                    if (player.hasPermission("economy.modo")) {

                        if (strings.length > 2) {

                            try {

                                Player receiver = Bukkit.getPlayer(strings[1]);
                                double amount = Double.parseDouble(strings[2]);

                                if (receiver != null) {

                                    receiver.sendMessage("Dieu vous a ajouté " + mainColor + strings[2] + moneySymbol + " " + ChatColor.RESET + "sur votre compte.");
                                    player.sendMessage("Vous venez d'ajouté " + mainColor + strings[2] + moneySymbol + " " + ChatColor.RESET + "sur le compte de " + mainColor + receiver.getName() + ChatColor.RESET + ".");
                                    Database.addBankFunds(receiver, amount);

                                } else {

                                    playerError(player);

                                }

                                return true;

                            } catch (NumberFormatException e) {

                                e.printStackTrace();
                                amountError(player);
                                return true;

                            }
                        }

                        player.sendMessage(commandsArgs.get(strings[0]));
                        return true;
                    }

                    accessError(player);
                    return true;

                case "set":

                    if (player.hasPermission("economy.modo")) {

                        if (strings.length > 2) {

                            try {

                                Player receiver = Bukkit.getPlayer(strings[1]);
                                int amount = Integer.parseInt(strings[2]);

                                if (receiver != null) {

                                    receiver.sendMessage("Dieu a fixé votre compte à " + mainColor + amount + moneySymbol + ChatColor.RESET + ".");
                                    player.sendMessage("Vous venez de définir le compte de " + mainColor + receiver.getName() + " §fà §6" + amount + "£§f.");
                                    Database.setBankFunds(player, amount);

                                } else {

                                    playerError(player);

                                }

                            } catch (NumberFormatException e) {

                                e.printStackTrace();
                                amountError(player);
                                return true;

                            }
                        }

                        player.sendMessage(commandsArgs.get(strings[0]));
                        return true;
                    }

                    accessError(player);
                    return true;

                case "sub":

                    if (player.hasPermission("economy.modo")) {

                        if (strings.length > 2) {

                            try {

                                Player receiver = Bukkit.getPlayer(strings[1]);
                                double amount = Double.parseDouble(strings[2]);

                                if (receiver != null) {

                                    receiver.sendMessage("Dieu a retiré " + mainColor + strings[2] + moneySymbol + " " + ChatColor.RESET + "de votre porte-monnaie.");
                                    player.sendMessage("Vous venez de retirer " + mainColor + strings[2] + moneySymbol + " " + ChatColor.RESET + "du porte-monnaie de " + mainColor + receiver.getName() + ChatColor.RESET + ".");
                                    Database.subBankFunds(receiver, amount);

                                } else {

                                    playerError(player);
                                }

                                return true;

                            } catch (NumberFormatException e) {

                                e.printStackTrace();

                            }
                        }

                        player.sendMessage(commandsArgs.get(strings[0]));
                        return true;
                    }

                    accessError(player);
                    return true;

                case "ranking":

                    List<AbstractMap.SimpleEntry<Player, Double>> accounts = Database.getAllBankFunds();

                    accounts.sort(Comparator.comparing(AbstractMap.SimpleEntry::getValue));

                    int i = 1;

                    for (AbstractMap.SimpleEntry<Player, Double> entry : accounts){

                        String rank;

                        switch (i){

                            case 1:

                                rank = ChatColor.YELLOW + "1er";
                                break;

                            case 2:

                                rank = ChatColor.GRAY + "2ème";
                                break;

                            case 3:

                                rank = ChatColor.GOLD + "3ème";
                                break;

                            default:

                                rank = i + "ème";

                        }

                        player.sendMessage(rank + ChatColor.RESET  + " : " + entry.getKey().getDisplayName() + " - " + entry.getValue() + moneySymbol);

                        i++;

                    }

                    break;

                case "give":

                    if (strings.length > 2) {

                        try {

                            Player receiver = Bukkit.getPlayer(strings[1]);
                            double amount = Double.parseDouble(strings[2]);

                            if (receiver != null) {

                                if (Database.getBankFunds(player) >= amount) {

                                    if (!receiver.getName().equals(player.getName())) {

                                        Database.addBankFunds(receiver, amount);
                                        receiver.sendMessage("Vous avez reçu " + mainColor + amount + moneySymbol + ChatColor.RESET + " de la part de " + mainColor + player.getName() + ChatColor.RESET + ".");
                                        Database.subBankFunds(player, amount);
                                        player.sendMessage("Vous avez donné " + mainColor + amount + moneySymbol + ChatColor.RESET + " à " + mainColor + receiver.getName() + ChatColor.RESET + ".");

                                    }

                                } else {

                                    player.sendMessage(errorColor + "Vous ne pouvez pas vous envoyer de l'argent !");

                                }

                            } else {

                                playerError(player);

                            }

                            return true;

                        } catch (NumberFormatException e){

                            e.printStackTrace();

                        }
                    }

                    player.sendMessage(commandsArgs.get(strings[0]));
                    return true;
            }

            player.sendMessage("-§6[§f|§6]§f-------------------§6Economy' Help§f------------------§6[§f|§6]§f-\n" +
                    "/money give <joueur> <montant> : Donnez de l'argent à un autre joueur.\n" +
                    "/money ranking : Affiche les joueurs classés en fonction de leur solde bancaire.");

            if (player.hasPermission("economy.modo")) {

                player.sendMessage("/money add <joueur> <montant> : Ajoute le montant au compte bancaire du joueur.\n" +
                        "/money sub <joueur> <montant> : Soustrait le montant au compte bancaire du joueur.\n" +
                        "/money set <joueur> <montant> : Défini le montant du compte bancaire du joueur.");
            }

            player.sendMessage("-§6[§f|§6]§f-------------------§6Economy' Help§f------------------§6[§f|§6]§f-");
            return true;
        }

        commandSender.sendMessage("Only players can use this command. ¯\\_(ツ)_/¯");
        return true;
    }

    private void accessError(Player player){

        player.sendMessage(errorColor + "Vous n'avez pas accès à cette commande.");

    }

    private void amountError(Player player){

        player.sendMessage(errorColor + "Le montant doit être un nombre.");

    }

    private void playerError(Player player){

        player.sendMessage(errorColor + "Ce joueur n'est pas connecté.");

    }
}
