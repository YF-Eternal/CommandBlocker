package org.commandblocker.commands;

import org.commandblocker.CommandBlocker;
import org.commandblocker.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandBlockerCMD implements CommandExecutor, TabCompleter {
    private final CommandBlocker plugin;

    // 构造方法
    public CommandBlockerCMD(CommandBlocker plugin) {
        this.plugin = plugin;
    }

    // 执行命令
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("commandblocker.admin")) {
            ChatUtils.chat(sender, plugin.getSettingsManager().getConfig().getString("Message"));
            return true;
        }

        if (args.length == 0) {
            args = new String[]{"help"};
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "reload":
                plugin.getSettingsManager().reload();
                ChatUtils.chat(sender, "<green><bold>CommandBlocker</bold> <dark_gray>» <green>配置文件重新加载成功!");
                return true;

            case "version":
                ChatUtils.chat(sender, "<green><bold>CommandBlocker</bold> <dark_gray>» <green>当前版本: <white>" + plugin.getDescription().getVersion());
                return true;

            default:
                ChatUtils.chat(sender, "<green><bold>CommandBlocker 命令:");
                ChatUtils.chat(sender, "<green><click:suggest_command:\"/cb reload\">/cb reload</click> <dark_gray>» <white>重新加载所有配置文件");
                ChatUtils.chat(sender, "<green><click:suggest_command:\"/cb version\">/cb version</click> <dark_gray>» <white>显示插件版本");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!sender.hasPermission("commandblocker.admin")) {
            return Collections.emptyList();
        }

        if (args.length < 2) {
            return Arrays.asList("help", "reload", "version");
        }

        return Collections.emptyList();
    }
}
