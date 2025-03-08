package com.karpen.lBanking.services;

import com.karpen.lBanking.models.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandService {

    private User user = new User();

    private BankService service;

    public CommandService(JavaPlugin plugin){
        service = new BankService(plugin);
    }

    public boolean help(Player player){
        player.sendMessage(ChatColor.DARK_AQUA + "/bank balance - проверить свой баланс");
        player.sendMessage(ChatColor.DARK_AQUA + "/bank send <ник игрока> <сумма> - перевести деньги");
        player.sendMessage(ChatColor.DARK_AQUA + "Чтобы положить или получить деньги, читай дискорд.");

        return true;
    }

    public boolean helpStaff(Player player){
        player.sendMessage(ChatColor.DARK_AQUA + "/bank-staff getbal <ник игрока> - получить баланс");
        player.sendMessage(ChatColor.DARK_AQUA + "/bank-staff addbal <ник игрока> <сумма> - положить на баланс");
        player.sendMessage(ChatColor.DARK_AQUA + "/bank-staff rembal <ник игрока> <сумма> - снять с баланса");

        return true;
    }

    public boolean getBalance(Player player){
        service.loadUsers();
        user = service.getUser(player.getName());

        player.sendMessage(ChatColor.DARK_AQUA + "Ваш баланс " + user.getBalance());

        return true;
    }

    public boolean sendPay(Player player, String targetName, int sum){
        service.loadUsers();

        service.balancePlus(targetName, sum);
        service.balanceMinus(player.getName(), sum);

        service.saveUsers();

        return true;
    }

    public boolean getPlayerBal(Player player, Player source){
        service.loadUsers();

        source.sendMessage(ChatColor.DARK_AQUA + "Баланс игрока " + player.getName() + " " + service.getUser(player.getName()).getBalance());

        return true;
    }

    public boolean addPlayerBal(Player player, int sum){
        service.loadUsers();

        service.getUser(player.getName()).setBalance(service.getUser(player.getName()).getBalance() + sum);

        service.saveUsers();

        return true;
    }

    public boolean remPlayerBal(Player player, int sum){
        service.loadUsers();

        service.getUser(player.getName()).setBalance(service.getUser(player.getName()).getBalance() - sum);

        service.saveUsers();

        return true;
    }
}
