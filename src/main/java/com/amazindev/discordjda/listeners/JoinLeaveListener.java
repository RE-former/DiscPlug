package com.amazindev.discordjda.listeners;

import com.amazindev.discordjda.DiscordFunctions;
import com.amazindev.discordjda.DiscordJDA;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.time.Instant;

public class JoinLeaveListener implements Listener {

    private Plugin plugin = DiscordJDA.getPlugin(DiscordJDA.class);
    String serverName;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        serverName = plugin.getConfig().getString("serverName");
        if (p.hasPlayedBefore()) {
            EmbedBuilder embedPlayerJoinedBefore = new EmbedBuilder()
                    .setTitle("User joined")
                    .setDescription(p.getName() + " has joined the server, his ip is " + p.getAddress().getHostString() + " \nHe logged in at " + p.getLocation().getBlockX() + " " + p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ() + " with " + (int)p.getHealth() + " health")
                    .setColor(Color.GREEN)
                    .setFooter(serverName)
                    .setThumbnail("https://cravatar.eu/helmavatar/" + p.getName() + "/64.png")
                    .setTimestamp(Instant.now());

            for (String id : plugin.getConfig().getStringList("ids")) {
                DiscordFunctions.sendEmbed(id, embedPlayerJoinedBefore.build());
            }
        } else {
            EmbedBuilder embedPlayerNotJoinedBefore = new EmbedBuilder()
                    .setTitle("User joined")
                    .setDescription(p.getName() + " has joined the server for the first time, his ip is " + p.getAddress().getHostString())
                    .setColor(Color.YELLOW)
                    .setFooter(serverName)
                    .setThumbnail("https://cravatar.eu/helmavatar/" + p.getName() + "/64.png")
                    .setTimestamp(Instant.now());

            for (String id : plugin.getConfig().getStringList("ids")) {
                DiscordFunctions.sendEmbed(id, embedPlayerNotJoinedBefore.build());
            }
        }



    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        EmbedBuilder embedLeave = new EmbedBuilder()
                .setTitle("User left")
                .setDescription(p.getName() + " has left the server at " + p.getLocation().getBlockX() + " " + p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ())
                .setColor(Color.RED)
                .setFooter(serverName)
                .setThumbnail("https://cravatar.eu/helmavatar/" + p.getName() + "/64.png")
                .setTimestamp(Instant.now());

        for (String id : plugin.getConfig().getStringList("ids")) {
            DiscordFunctions.sendEmbed(id, embedLeave.build());
        }
    }
}
