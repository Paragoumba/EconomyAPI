package fr.paragoumba.minediversity.economyapi.commands;

import fr.paragoumba.minediversity.economyapi.Database;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static fr.paragoumba.minediversity.economyapi.EconomyAPI.mainColor;
import static fr.paragoumba.minediversity.economyapi.EconomyAPI.moneySymbol;

public class TombolaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player)sender;

            switch(msg){

                case "play":

                    if (!Database.getTombolaParticipant(player)) {

                        if (Database.getBankFunds(player) >= 50) {

                            Database.subBankFunds(player, 50);
                            Database.addTombolaParticipant(player);
                            player.sendMessage("Vous participez maintenant à tombola.");
                            player.sendMessage(mainColor + "50" + moneySymbol + ChatColor.RESET + " vous ont été retirés.");

                        } else {

                            player.sendMessage("Vous n'avez pas assez d'argent sur votre compte bancaire pour participer. Il vous faut " + mainColor + "50" + ChatColor.RESET + "£");

                        }

                    } else {

                        player.sendMessage("Vous participez déjà à la tombola.");

                    }
                    break;

                case "infos":

                    player.sendMessage("Une tombola est organisée chaque jour. La participation coûte " + moneySymbol + " et se fait avec la commande /tombola participe");
            }
        }

        return false;
    }
}
