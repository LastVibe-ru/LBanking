package com.karpen.lBanking.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karpen.lBanking.models.User;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BankService {

    private File file;
    private Gson gson;
    private JavaPlugin plugin;

    private User user = new User();
    private List<User> users = new ArrayList<>();

    public BankService(JavaPlugin plugin){
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "users.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        if (!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdirs();
        }
    }

    public void addUser(String name){
        loadUsers();

        user.setName(name);
        user.setBalance(0);

        users.add(user);
        saveUsers();
    }

    public boolean balancePlus(String name, int sum){
        loadUsers();

        user = getUser(name);
        if (user == null){
            return false;
        }

        user.setBalance(user.getBalance() + sum);
        saveUsers();

        return true;
    }

    public boolean balanceMinus(String name, int sum){
        loadUsers();

        user = getUser(name);
        if (user == null){
            return false;
        }

        user.setBalance(user.getBalance() - sum);
        saveUsers();

        return true;
    }

    public User getUser(String name){
        loadUsers();

        for (User user : users){
            if (user.getName().equalsIgnoreCase(name)){
                return user;
            }
        }

        return null;
    }

    public void saveUsers(){
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(users, writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void loadUsers(){
        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
        }

        try(FileReader reader = new FileReader(file)) {
            users = List.of(gson.fromJson(reader, User[].class));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
