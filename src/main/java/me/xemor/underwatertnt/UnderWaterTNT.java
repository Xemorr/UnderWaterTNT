package me.xemor.underwatertnt;

import me.xemor.underwatertnt.commands.UnderWaterTNTCommand;
import me.xemor.underwatertnt.commands.UnderWaterTNTTabCompletion;
import me.xemor.underwatertnt.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.block.data.type.TNT;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class UnderWaterTNT extends JavaPlugin {

    private static UnderWaterTNT instance;
    private Config config;

    @Override
    public void onEnable() {
        instance = this;
        config = new Config();

        Bukkit.getPluginCommand("uwt").setExecutor(new UnderWaterTNTCommand());
        Bukkit.getPluginCommand("uwt").setTabCompleter(new UnderWaterTNTTabCompletion());

        Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(), this);

        // sending a enabled message with plugin name and version number
        PluginDescriptionFile pdfFile = this.getDescription();
        Bukkit.getLogger().info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " is now enabled!");
    }

    @Override
    public void onDisable() {
        // sending a enabled message with plugin name and version number
        PluginDescriptionFile pdfFile = this.getDescription();
        Bukkit.getLogger().info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " is now disabled!");
    }

    public static UnderWaterTNT getInstance() {
        return instance;
    }

    public Config getConfiguration() {
        return config;
    }

    public Config reload() {
        return config = new Config();
    }
}
