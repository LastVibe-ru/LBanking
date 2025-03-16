package com.karpen.lBanking.commands;

import com.karpen.lBanking.services.CommandService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BankCommands implements CommandExecutor {

    private final CommandService service;

    public BankCommands(JavaPlugin plugin) {
        service = new CommandService(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Не юзай для этих команд консоль пж");
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.sendMessage(ChatColor.RED + "Используйте /bank help для подсказки");
            return true;
        }

        return switch (strings[0].toLowerCase()) {
            case "balance" -> service.getBalance(player);
            case "send" -> sendPay(strings, player);
            case "help" -> service.help(player);
            default -> def(player);
        };
    }

    private boolean def(Player player) {
        player.sendMessage(ChatColor.RED + "Используйте /bank help для подсказки");
        return true;
    }

    private boolean sendPay(String[] strings, Player player) {
        if (strings.length != 3) {
            player.sendMessage(ChatColor.RED + "Используйте /bank help для подсказки");
            return true;
        }

        try {
            int amount = Integer.parseInt(strings[2]);
            //player.sendMessage(ChatColor.DARK_AQUA + "Вы успешно перевели " + amount + " игроку " + strings[1]);
            return service.sendPay(player, strings[1].toLowerCase(), amount);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Введите корректную сумму для отправки.");
            return true;
        }
    }
}