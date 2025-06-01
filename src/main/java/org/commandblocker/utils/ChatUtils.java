package org.commandblocker.utils;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    private static BukkitAudiences adventure;

    public static void setAdventure(BukkitAudiences adv) {
        adventure = adv;
    }

    /**
     * A quick way to send a CommandSender a colored message.
     *
     * @param sender  CommandSender to send message to.
     * @param message The message being sent.
     */
    public static void chat(CommandSender sender, String message) {
        adventure.sender(sender).sendMessage(translate(message));
    }

    /**
     * Translates a String to a colorful String using methods in the BungeeCord API.
     *
     * @param message Message to translate.
     * @return Translated Message.
     */
    public static Component translate(String message) {
        return MiniMessage.miniMessage().deserialize(replaceLegacy(message));
    }

    /**
     * Replaces the legacy color codes used in a message with their MiniMessage counterparts.
     *
     * @param message Message to replace color codes in.
     * @return Message with the color codes replaced.
     */
    public static String replaceLegacy(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        // 获取服务器版本号的中间数字，如 "1.20.6-R0.1-SNAPSHOT" → 20
        int majorVersion = 0;
        try {
            String bukkitVersion = Bukkit.getBukkitVersion(); // "1.20.6-R0.1-SNAPSHOT"
            String[] parts = bukkitVersion.split("\\.");
            if (parts.length >= 2) {
                majorVersion = Integer.parseInt(parts[1]);
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("[CommandBlocker] Failed to determine server version. Defaulting to legacy format.");
        }

        // 如果版本 >= 16 支持 hex 颜色代码（格式 &#rrggbb）
        if (majorVersion >= 16) {
            Pattern pattern = Pattern.compile("&#([a-fA-F0-9]{6})");
            Matcher matcher = pattern.matcher(message);
            StringBuffer buffer = new StringBuffer();

            while (matcher.find()) {
                String hex = matcher.group(1);
                matcher.appendReplacement(buffer, "<reset><color:#" + hex + ">");
            }
            matcher.appendTail(buffer);
            message = buffer.toString();
        }

        // 将 § 替换为 &（支持 §1 或 §a 等）
        message = message.replace("§", "&");

        // 替换 legacy color codes 到 MiniMessage
        return message
                .replace("&0", "<reset><black>")
                .replace("&1", "<reset><dark_blue>")
                .replace("&2", "<reset><dark_green>")
                .replace("&3", "<reset><dark_aqua>")
                .replace("&4", "<reset><dark_red>")
                .replace("&5", "<reset><dark_purple>")
                .replace("&6", "<reset><gold>")
                .replace("&7", "<reset><gray>")
                .replace("&8", "<reset><dark_gray>")
                .replace("&9", "<reset><blue>")
                .replace("&a", "<reset><green>")
                .replace("&b", "<reset><aqua>")
                .replace("&c", "<reset><red>")
                .replace("&d", "<reset><light_purple>")
                .replace("&e", "<reset><yellow>")
                .replace("&f", "<reset><white>")
                .replace("&k", "<obfuscated>")
                .replace("&l", "<bold>")
                .replace("&m", "<strikethrough>")
                .replace("&n", "<underlined>") // 以前是 <u>，但 <underlined> 更标准
                .replace("&o", "<italic>")
                .replace("&r", "<reset>");
    }
}