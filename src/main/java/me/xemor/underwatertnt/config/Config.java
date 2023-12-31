package me.xemor.underwatertnt.config;

import me.xemor.underwatertnt.UnderWaterTNT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {

	private HashMap<String, String> messages;
	private List<Material> blocks;
	private int radius;

	public Config() {

		messages = new HashMap<>();
		blocks = new ArrayList<>();
		radius = 3;

		File file = new File(UnderWaterTNT.getInstance().getDataFolder() + "/config.yml");
		if (!file.exists())
			UnderWaterTNT.getInstance().saveDefaultConfig();
		initializeConfig();
	}

	public void initializeConfig() {
		File file = new File(UnderWaterTNT.getInstance().getDataFolder() + "/config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		if (config.isSet("prefix")) {
			messages.put("prefix", config.getString("prefix"));
		}
		if (config.isSet("messages")) {
			config.getConfigurationSection("messages").getKeys(true).forEach(key -> {
				messages.put(key, config.getString("messages." + key).replace("&", "§")
						.replace("{prefix}", messages.get("prefix")).replace("\n", "\n").replace("\\n", "\n"));
			});
		}

		PluginDescriptionFile pdfFile = UnderWaterTNT.getInstance().getDescription();

		if (config.isSet("cannon_blocks")) {
			config.getStringList("cannon_blocks").forEach(block -> {
				try {
					blocks.add(Material.valueOf(block));
				} catch (Exception exception) {
					Bukkit.getLogger().info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " " + block
							+ " is not a valid block type!");
				}
			});
		}

		if (config.isSet("radius")) {
			radius = config.getInt("radius");
		}
	}

	public String getMessage(String message) {
		if (messages.containsKey(message)) {
			return messages.get(message).replace("&", "§").replace("\n", "\n").replace("\\n", "\n");
		} else {
			return "missing message '" + message + "'";
		}
	}

	public int getRadius() {
		return radius;
	}

	public List<Material> getCannonBlocks() {
		return blocks;
	}

	public void setRadius(int radius) {
		this.radius = radius;

		File file = new File(UnderWaterTNT.getInstance().getDataFolder() + "/config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		config.set("radius", radius);

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
