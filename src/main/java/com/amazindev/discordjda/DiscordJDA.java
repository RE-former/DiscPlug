package com.amazindev.discordjda;

import com.amazindev.discordjda.commands.ReloadCommand;
import com.amazindev.discordjda.discordCommands.PingListener;
import com.amazindev.discordjda.discordCommands.PlayerListListener;
import com.amazindev.discordjda.listeners.ChatListener;
import com.amazindev.discordjda.listeners.DieListener;
import com.amazindev.discordjda.listeners.JoinLeaveListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

public final class DiscordJDA extends JavaPlugin implements CommandExecutor {

    public static JDA jda;
    String token;
    String activity;
    String serverName;
    @Override
    public void onEnable() {

        // Loading config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (!getConfig().contains("token")) {
            getConfig().set("token", "OTAxMzkxODY1Nzg0NDU5Mjg1.YXPMlw.pXjnhPz_myuCETqUbXyWKsiMAxg");
        }
        if (!getConfig().contains("serverName")) {
            getConfig().set("serverName", "Server Build");
        }
        if (!getConfig().contains("activity")) {
            getConfig().set("activity", "mclsmi.lyceestendhal.it");
        }
        if (!getConfig().contains("ids")) {
            ArrayList<String> ids = new ArrayList<>();
            ids.add("561243464721891330");
            ids.add("561243464721891330");
            getConfig().set("ids", ids);
        }
        if (!getConfig().contains("prefix")) {
            getConfig().set("prefix", ">");
        }
        if (!getConfig().contains("bad_wordsEnabled")) {
            getConfig().set("bad_wordsEnabled", false);
        }
        if (!getConfig().contains("bad_words")) {
            ArrayList<String> ids = new ArrayList<>();
            ids.add("fuck");
            ids.add("shit");
            getConfig().set("bad_words", ids);
        }
        saveConfig();

        token = getConfig().getString("token");
        activity = getConfig().getString("activity");
        serverName = getConfig().getString("serverName");

        // Registering events
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new DieListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        // Registering commands
        getCommand("discordjdareload").setExecutor(new ReloadCommand());

        // Starting bot
        try {
            jda = JDABuilder.createDefault(token)
                    .setStatus(OnlineStatus.IDLE)
                    .setActivity(Activity.watching(activity))
                    .build()
                    .awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        // Registering discord commands
        jda.addEventListener(new PlayerListListener());
        jda.addEventListener(new PingListener());

        // Enabling metrics
        Metrics metrics = new Metrics(this, 13417);

        // Sending server start message to user
        for (String id : this.getConfig().getStringList("ids")) {
            DiscordFunctions.sendDM(id, ":white_check_mark: " + "**" + serverName + "**" + " has started!");
        }
    }

    @Override
    public void onDisable() {
        if (jda != null) {
            for (String id : this.getConfig().getStringList("ids")) {
                DiscordJDA.jda.openPrivateChannelById(id).complete().sendMessage(":octagonal_sign: " + "**" + serverName + "**" + " has stopped!").complete();
            }
            jda.shutdownNow();
        }
    }
}
