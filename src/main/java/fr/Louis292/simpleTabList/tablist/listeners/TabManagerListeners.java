package fr.Louis292.simpleTabList.tablist.listeners;

import fr.Louis292.simpleTabList.SimpleTabList;
import fr.Louis292.simpleTabList.tablist.TabManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class TabManagerListeners implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        TabManager.removePlayerTab(event.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        List<String> header = new ArrayList<>();
        for (String s : SimpleTabList.HEADER_LINES) {
            header.add(ChatColor.translateAlternateColorCodes('&', s.replace("{PLAYER_NAME}", player.getName())));
        }

        List<String> footer = new ArrayList<>();
        for (String s : SimpleTabList.FOOTER_LINES) {
            footer.add(ChatColor.translateAlternateColorCodes('&', s.replace("{PLAYER_NAME}", player.getName())));
        }

        TabManager.updatePlayerTab(player, header, footer);
    }
}
