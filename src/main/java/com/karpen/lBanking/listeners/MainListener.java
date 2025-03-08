package com.karpen.lBanking.listeners;

import com.karpen.lBanking.services.BankService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MainListener implements Listener {

    private BankService service;

    public MainListener(JavaPlugin plugin){
        service = new BankService(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        service.loadUsers();

        if (service.getUser(player.getName()) == null){
            service.addUser(player.getName());

            service.saveUsers();
        }
    }
}
