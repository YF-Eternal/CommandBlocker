package org.commandblocker.listeners;

import org.commandblocker.CommandBlocker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.List;

public class PlayerCommandSendListener implements Listener {
    private final CommandBlocker plugin; // 插件

    // 构造方法
    public PlayerCommandSendListener(CommandBlocker plugin) {
        this.plugin = plugin;
    }

    // 处理玩家发送的命令预处理事件
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        List<String> commands = plugin.getSettingsManager().getConfig().getStringList("Commands");

        // 检查玩家是否有绕过权限
        if (event.getPlayer().hasPermission("commandblocker.bypass")) {
            return;
        }

        // 对命令进行拦截和处理
        String[] commandParts = event.getMessage().split(" ");
        for (String command : commands) {
            if (command.equals("*")) {
                continue;
            }
            if (command.endsWith("[all]")) {
                String commandWithoutAll = command.substring(0, command.length() - 6).trim();
                if (event.getMessage().startsWith(commandWithoutAll)) {
                    return;
                }
            }
            String[] parts = command.split(" ");
            if (parts.length == 1 && parts[0].equals("*")) {
                continue;
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

        // 设置事件取消，发送拒绝消息
        event.setCancelled(true);
        event.getPlayer().sendMessage("不允许使用该命令!");
    }
}
