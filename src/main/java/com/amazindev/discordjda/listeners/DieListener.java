package com.amazindev.discordjda.listeners;

import com.amazindev.discordjda.DiscordFunctions;
import com.amazindev.discordjda.DiscordJDA;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.time.Instant;

public class DieListener implements Listener {

    private Plugin plugin = DiscordJDA.getPlugin(DiscordJDA.class);
    String serverName;

    @EventHandler
    public void onDed(PlayerDeathEvent e) {

        Player dedPlayer = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();

        if (killer == null) {
            return;
        }

        serverName = plugin.getConfig().getString("serverName");

        EmbedBuilder embedDed = new EmbedBuilder()
                .setTitle("User got killed")
                .setDescription(dedPlayer.getName() + " got killed by " + killer.getName() +  " at " + dedPlayer.getLocation().getBlockX() + " " + dedPlayer.getLocation().getBlockY() + " " + dedPlayer.getLocation().getBlockZ() + " and has " + (int)killer.getHealth() + " healh")
                .setColor(Color.BLACK)
                .setFooter(serverName)
                .setTimestamp(Instant.now());


        for (String id : plugin.getConfig().getStringList("ids")) {
            DiscordFunctions.sendEmbed(id, embedDed.build());
        }
    }
}
