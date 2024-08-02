package org.commandblocker;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SettingsManager {
    private FileConfiguration config; // 配置信息
    private final File configFile; // 配置文件

    // 加载或创建配置文件
    public SettingsManager(Plugin plugin) {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    // 获取配置信息
    public FileConfiguration getConfig() {
        return config;
    }

    // 重新加载配置文件
    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

}
