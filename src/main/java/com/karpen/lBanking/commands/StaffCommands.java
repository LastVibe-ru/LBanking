package com.karpen.lBanking.commands;

import com.karpen.lBanking.services.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StaffCommands implements CommandExecutor {

    private final CommandService service;

    public StaffCommands(JavaPlugin plugin) {
        service = new CommandService(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Не юзай с консоли пж");
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.sendMessage(ChatColor.RED + "Используйте /bank-staff help для подсказки");
            return true;
        }

        return switch (strings[0].toLowerCase()) {
            case "help" -> service.helpStaff(player);
            case "getbal" -> getPlayerBal(strings, player);
            case "addbal" -> addPlayerBal(strings, player);
            case "rembal" -> remPlayerBal(strings, player);
            default -> def(player);
        };
    }

    private boolean def(Player player) {
        player.sendMessage(ChatColor.RED + "Используйте /bank-staff help для подсказки");
        return true;
    }

    private boolean getPlayerBal(String[] strings, Player player) {
        if (strings.length != 2) {
            player.sendMessage(ChatColor.RED + "Используйте /bank-staff help для подсказки");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(strings[1].toLowerCase());
        if (targetPlayer == null) {
            player.sendMessage(ChatColor.RED + "Игрок не найден");
            return true;
        }

        return service.getPlayerBal(player, targetPlayer);
    }

    private boolean addPlayerBal(String[] strings, Player player) {
        if (strings.length != 3) {
            player.sendMessage(ChatColor.RED + "Используйте /bank-staff help для подсказки");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(strings[1].toLowerCase());
        if (targetPlayer == null) {
            player.sendMessage(ChatColor.RED + "Игрок не найден");
            return true;
        }

        try {
            int amount = Integer.parseInt(strings[2]);
            player.sendMessage(ChatColor.DARK_AQUA + "Добавлено " + amount + " к балансу игрока " + targetPlayer.getName());
            return service.addPlayerBal(targetPlayer, amount);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Введите корректную сумму для добавления.");
            return true;
        }
    }

    private boolean remPlayerBal(String[] strings, Player player) {
        if (strings.length != 3) {
            player.sendMessage(ChatColor.RED + "Используйте /bank-staff help для подсказки");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(strings[1].toLowerCase());
        if (targetPlayer == null) {
            player.sendMessage(ChatColor.RED + "Игрок не найден");
            return true;
        }

        try {
            int amount = Integer.parseInt(strings[2]);
            player.sendMessage(ChatColor.DARK_AQUA + "Снято " + amount + " с баланса игрока " + targetPlayer.getName());
            return service.remPlayerBal(targetPlayer, amount);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Введите корректную сумму для снятия.");
            return true;
        }
    }
}
