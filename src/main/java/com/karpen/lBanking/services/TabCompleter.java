package com.karpen.lBanking.services;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> suggestions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("bank")) {
            if (strings.length == 1) {
                suggestions.add("balance");
                suggestions.add("send");
                suggestions.add("help");
            } else if (strings.length == 2) {
                for (var player : Bukkit.getOnlinePlayers()) {
                    suggestions.add(player.getName());
                }
            }
        }

        if (command.getName().equalsIgnoreCase("bank-staff")) {
            if (strings.length == 1) {
                suggestions.add("getbal");
                suggestions.add("addbal");
                suggestions.add("rembal");
                suggestions.add("help");
            } else if (strings.length == 2) {
                for (var player : Bukkit.getOnlinePlayers()) {
                    suggestions.add(player.getName());
                }
            }
        }

        return suggestions;
    }
}
