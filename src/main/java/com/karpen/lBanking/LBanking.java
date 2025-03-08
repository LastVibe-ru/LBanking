package com.karpen.lBanking;

import com.karpen.lBanking.commands.BankCommands;
import com.karpen.lBanking.commands.StaffCommands;
import com.karpen.lBanking.listeners.MainListener;
import com.karpen.lBanking.services.TabCompleter;
import com.karpen.lBanking.services.WebService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LBanking extends JavaPlugin {

    private WebService webService;

    @Override
    public void onEnable() {
        getLogger().info("LBanking v1.0 loaded!");

        BankCommands bankCommands = new BankCommands(this);
        StaffCommands staffCommands = new StaffCommands(this);
        MainListener mainListener = new MainListener(this);
        TabCompleter completer = new TabCompleter();
        webService = new WebService(this);

        Bukkit.getPluginManager().registerEvents(mainListener, this);

        Objects.requireNonNull(getCommand("bank")).setExecutor(bankCommands);
        Objects.requireNonNull(getCommand("bank-staff")).setExecutor(staffCommands);

        Objects.requireNonNull(getCommand("bank")).setTabCompleter(completer);
        Objects.requireNonNull(getCommand("bank-staff")).setTabCompleter(completer);

        webService.start();

    }

    @Override
    public void onDisable() {
        getLogger().info("LBanking v1.0 disabled");

        webService.shutdown();
    }
}
