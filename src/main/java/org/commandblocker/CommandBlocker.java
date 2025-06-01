package org.commandblocker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.commandblocker.commands.CommandBlockerCMD;
import org.commandblocker.listeners.PlayerCommandPreprocessListener;
import org.commandblocker.utils.ChatUtils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandBlocker extends JavaPlugin {
    private BukkitAudiences adventure; // Adventure 组件
    private SettingsManager settingsManager; // 设置管理器

    @Override
    public void onEnable() {
        // 插件启用逻辑

        // 初始化 Adventure 组件实例
        this.adventure = BukkitAudiences.create(this);
        ChatUtils.setAdventure(adventure);

        settingsManager = new SettingsManager(this); // 初始化设置管理器

        // 设置命令执行器和事件监听器
        getCommand("commandblocker").setExecutor(new CommandBlockerCMD(this));
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(this), this);

        // 发送加载成功的信息到控制台
        ChatUtils.chat(Bukkit.getConsoleSender(), "&bCommandBlocker 已加载!");
        getLogger().info("作者: YF_Eternal");
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }

        // 发送加载成功的信息到控制台
        ChatUtils.chat(Bukkit.getConsoleSender(), "&bCommandBlocker 已卸载!");
    }

    // 获取设置管理器实例
    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
}
