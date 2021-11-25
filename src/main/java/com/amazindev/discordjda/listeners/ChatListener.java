package com.amazindev.discordjda.listeners;

import com.amazindev.discordjda.DiscordFunctions;
import com.amazindev.discordjda.DiscordJDA;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.time.Instant;
import java.util.List;

public class ChatListener implements Listener {
    private Plugin plugin = DiscordJDA.getPlugin(DiscordJDA.class);
    String serverName;
    Boolean bad_wordsEnabled;
    List<String> bad_words;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        bad_wordsEnabled = plugin.getConfig().getBoolean("bad_wordsEnabled");
        if (bad_wordsEnabled) {
            bad_words = plugin.getConfig().getStringList("bad_words");
            serverName = plugin.getConfig().getString("serverName");
            if (!e.getPlayer().hasPermission("discordjda.chatbypass")) {
                for (String word : bad_words) {
                    Player p = e.getPlayer();
                    if (e.getMessage().contains(word)) {
                        e.setCancelled(true);
                        p.sendMessage(ChatColor.RED + "You cannot say this word!");
                        plugin.getLogger().info(ChatColor.RED + e.getPlayer().getName() + " tried to say the word " + word);

                        EmbedBuilder embedSwear = new EmbedBuilder()
                                .setTitle("User swore")
                                .setDescription(p.getDisplayName() + " said `"  + word + "`")
                                .setColor(Color.GRAY)
                                .setFooter(serverName)
                                .setTimestamp(Instant.now());

                        for (String id : plugin.getConfig().getStringList("ids")) {
                            DiscordFunctions.sendEmbed(id, embedSwear.build());
                        }

                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            if (player.hasPermission("discordjda.swearcheck")) {
                                player.sendMessage(ChatColor.RED + e.getPlayer().getName() + " tried to say the word " + word);
                            }
                        }

                    }
                }
            }
        }
    }
}
