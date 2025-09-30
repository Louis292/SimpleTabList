package fr.Louis292.simpleTabList;

import fr.Louis292.simpleTabList.tablist.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SimpleTabList extends JavaPlugin {
    public static FileConfiguration config;

    public static List<String> HEADER_LINES = new ArrayList<>();
    public static List<String> FOOTER_LINES = new ArrayList<>();

    public static TabManager tabManager;

    @Override
    public void onEnable() {
        getLogger().info("Starting...");

        this.saveDefaultConfig();
        config = getConfig();

        HEADER_LINES= config.getStringList("header");
        FOOTER_LINES = config.getStringList("footer");

        tabManager = new TabManager(this);

        getLogger().info("The plugin was been start !");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling...");
        for (Player p : Bukkit.getOnlinePlayers()) {
            TabManager.removePlayerTab(p);
        }
        getLogger().info("The plugin was been disable");
    }
}
