package me.xemor.underwatertnt.commands;

import me.xemor.underwatertnt.UnderWaterTNT;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnderWaterTNTCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String albel, String[] args) {

		if (args.length == 1) {

			if (args[0].equalsIgnoreCase("setradius")) {
				if (!sender.hasPermission("uwt.setradius") && !sender.hasPermission("uwt.admin")) {
					sender.sendMessage(UnderWaterTNT.getInstance().getConfiguration().getMessage("missing_permission"));
					return true;
				}

				sender.sendMessage(UnderWaterTNT.getInstance().getConfiguration().getMessage("uwt_radius_usage"));

				return true;
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (!sender.hasPermission("uwt.reload") && !sender.hasPermission("uwt.admin")) {
					sender.sendMessage(UnderWaterTNT.getInstance().getConfiguration().getMessage("missing_permission"));
					return true;
				}

				UnderWaterTNT.getInstance().reload();
				sender.sendMessage(UnderWaterTNT.getInstance().getConfiguration().getMessage("prefix") + " §aPlugin reloaded!");

				return true;
			}
		}

		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("setradius")) {
				if (!sender.hasPermission("uwt.setradius") && !sender.hasPermission("uwt.admin")) {
					sender.sendMessage(UnderWaterTNT.getInstance().getConfiguration().getMessage("missing_permission"));
					return true;
				}

				try {
					int radius = Integer.parseInt(args[1]);

					sender.sendMessage(UnderWaterTNT.getInstance().getConfiguration().getMessage("uwt_radius_success")
							.replace("{radius}", radius + ""));

					UnderWaterTNT.getInstance().getConfiguration().setRadius(radius);
					
				} catch (Exception exception) {
					sender.sendMessage(UnderWaterTNT.getInstance().getConfiguration().getMessage("uwt_radius_wrong_numbers")
							.replace("{radius}", args[1]));
					return true;
				}
				return true;
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (!sender.hasPermission("uwt.reload") && !sender.hasPermission("uwt.admin")) {
					sender.sendMessage(UnderWaterTNT.getInstance().getConfiguration().getMessage("missing_permission"));
					return true;
				}

				UnderWaterTNT.getInstance().reload();
				sender.sendMessage(UnderWaterTNT.getInstance().getConfiguration().getMessage("prefix") + " §aPlugin reloaded!");

				return true;
			}
		}

		if (!sender.hasPermission("uwt.help") && !sender.hasPermission("uwt.admin")) {
			sender.sendMessage(UnderWaterTNT.getInstance().getConfiguration().getMessage("missing_permission"));
			return true;
		}

		sender.sendMessage("");
		sender.sendMessage("§8§m--------§8┃ §6§lUWT §8§m┃---------");
		sender.sendMessage("§8┃» ");
		sender.sendMessage("§8┃» §7Plugin by §6Takacick");
		sender.sendMessage("§8┃» §7radius: §6" + UnderWaterTNT.getInstance().getConfiguration().getRadius());
		sender.sendMessage("§8┃» ");
		sender.sendMessage("§8┃» /§7uwt §8- §7shows this page §8(§7uwt.help§8)");
		sender.sendMessage("§8┃» /§7uwt setradius §8- §7reload the config §8(§7uwt.radius§8)");
		sender.sendMessage("§8┃» /§7uwt reload §8- §7reset gametime §8(§7uwt.reload§8)");
		sender.sendMessage("§8┃» ");
		sender.sendMessage("§8§m--------§8┃ §6§lUWT §8§m┃---------");
		sender.sendMessage("");

		return true;
	}

}
