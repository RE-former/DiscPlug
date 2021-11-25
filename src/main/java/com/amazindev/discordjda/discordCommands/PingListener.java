package com.amazindev.discordjda.discordCommands;

import com.amazindev.discordjda.DiscordJDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.plugin.java.JavaPlugin;

public class PingListener extends ListenerAdapter {
    JavaPlugin plugin = DiscordJDA.getPlugin(DiscordJDA.class);
    String prefix;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        prefix = plugin.getConfig().getString("prefix");
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();

        if (content.equalsIgnoreCase(prefix + "ping")) {
            message.reply("Pong!").queue();
        }
    }
}
