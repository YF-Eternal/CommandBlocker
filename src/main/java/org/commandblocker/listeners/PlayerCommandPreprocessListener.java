package org.commandblocker.listeners;

import org.commandblocker.CommandBlocker;
import org.commandblocker.utils.ChatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.List;

public class PlayerCommandPreprocessListener implements Listener {
    private final CommandBlocker plugin;

    // 构造方法
    public PlayerCommandPreprocessListener(CommandBlocker plugin) {
        this.plugin = plugin;
    }

    // 处理玩家发送的命令预处理事件
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("commandblocker.bypass")) {
            return;
        }

        String[] commandParts = event.getMessage().split(" ");
        List<String> commands = plugin.getSettingsManager().getConfig().getStringList("Commands");
        for (String command : commands) {
            if (command.equals("*")) {
                return;
            }
            if (command.endsWith("[all]")) {
                String commandWithoutAll = command.substring(0, command.length() - 6).trim();
                if (event.getMessage().startsWith(commandWithoutAll)) {
                    return;
                }
            }
            String[] parts = command.split(" ");
            if (parts.length == 1 && parts[0].equals("*")) {
                return;
            }
            if (parts.length == commandParts.length) {
                boolean match = true;
                for (int i = 0; i < parts.length; i++) {
                    if (!parts[i].equals("*") && !parts[i].equalsIgnoreCase(commandParts[i])) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return;
                }
            }
        }

        ChatUtils.chat(event.getPlayer(), plugin.getSettingsManager().getConfig().getString("Message"));
        event.setCancelled(true);
    }
}
