package com.amazindev.discordjda.discordCommands;

import com.amazindev.discordjda.DiscordJDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.stream.Collectors;

public class PlayerListListener extends ListenerAdapter {
    private Plugin plugin = DiscordJDA.getPlugin(DiscordJDA.class);
    String serverName;
    String prefix;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        serverName = plugin.getConfig().getString("serverName");
        prefix = plugin.getConfig().getString("prefix");
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();

        if (content.equalsIgnoreCase(prefix + "playerlist")) {
            String onlinePlayers = Bukkit.getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .collect(Collectors.joining(", "));

            if (onlinePlayers.isEmpty()) {
                channel.sendMessage("There currently is currently anyone online in " + serverName).queue();
            } else {
                channel.sendMessage("The following user/s in " + serverName + ": \n```" + onlinePlayers + "```").queue();
            }
        }
    }
}
