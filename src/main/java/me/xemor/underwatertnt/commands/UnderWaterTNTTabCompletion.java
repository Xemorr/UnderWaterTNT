package me.xemor.underwatertnt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UnderWaterTNTTabCompletion implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return null;
		}

		if (args.length == 1) {
			List<String> help = new ArrayList<>();

			String searching = "";

			if (args[0] != null) {
				searching = args[0].toLowerCase();
			}

			if ("reload".startsWith(searching)) {
				if (sender.hasPermission("uwt.reload") || sender.hasPermission("uwt.admin")) {
					help.add("reload");
				}
			}

			if ("setradius".startsWith(searching)) {
				if (sender.hasPermission("uwt.setradius") || sender.hasPermission("uwt.admin")) {
					help.add("setradius");
				}
			}

			return help;
		} 

		return null;
	}

}
