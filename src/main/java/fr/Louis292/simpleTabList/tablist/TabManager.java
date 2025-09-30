package fr.Louis292.simpleTabList.tablist;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import fr.Louis292.simpleTabList.tablist.listeners.TabManagerListeners;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TabManager {
    private static JavaPlugin plugin;

    public TabManager(JavaPlugin plugin) {
        TabManager.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new TabManagerListeners(), plugin);
    }

    public static void updatePlayerTab(Player player, List<String> headerLines, List<String> footerLines) {
        String header = buildTabString(headerLines);
        String footer = buildTabString(footerLines);

        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        packet.getChatComponents().write(0, WrappedChatComponent.fromJson(convertToJson(header)));
        packet.getChatComponents().write(1, WrappedChatComponent.fromJson(convertToJson(footer)));

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removePlayerTab(Player player) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        packet.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\":\"\"}"));
        packet.getChatComponents().write(1, WrappedChatComponent.fromJson("{\"text\":\"\"}"));

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String buildTabString(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            sb.append(translateColorCodes(lines.get(i)));
            if (i < lines.size() - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private static String translateColorCodes(String text) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', text);
    }

    private static String convertToJson(String text) {
        text = text.replace("\\", "\\\\").replace("\"", "\\\"");
        return "{\"text\":\"" + text + "\"}";
    }
}