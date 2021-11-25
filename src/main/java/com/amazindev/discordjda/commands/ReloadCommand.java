package com.amazindev.discordjda.commands;

import com.amazindev.discordjda.DiscordJDA;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    JavaPlugin plugin = DiscordJDA.getPlugin(DiscordJDA.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("discordjda.reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Successfully reloaded config.");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command (discordjda.reload)");
        }

        return true;
    }
}
