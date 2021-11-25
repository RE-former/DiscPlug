package com.amazindev.discordjda;

import net.dv8tion.jda.api.entities.MessageEmbed;

public class DiscordFunctions {

    public static void sendDM(String id, String message) {
        DiscordJDA.jda.openPrivateChannelById(id).queue((channel) -> {
            channel.sendMessage(message).queue();
        });
    }

    public static void sendEmbed(String id, MessageEmbed message) {
        DiscordJDA.jda.openPrivateChannelById(id).queue((channel) -> {
            channel.sendMessageEmbeds(message).queue();
        });
    }
}
