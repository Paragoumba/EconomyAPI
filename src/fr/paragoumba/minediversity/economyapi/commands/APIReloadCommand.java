package fr.paragoumba.minediversity.economyapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static fr.paragoumba.minediversity.economyapi.EconomyAPI.init;

/**
 * Created by Paragoumba on 25/10/2017.
 */

public class APIReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender.hasPermission("economy.*")) {

            init();

        }

        return true;
    }
}
