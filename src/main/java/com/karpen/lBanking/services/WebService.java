package com.karpen.lBanking.services;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebService {

    private final JavaPlugin plugin;
    private HttpServer httpServer;

    public WebService(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public void start(){
        try {
            httpServer = HttpServer.create(new InetSocketAddress(8090), 0);
            httpServer.createContext("/api", new ApiHandler(plugin));
            httpServer.setExecutor(null);
            httpServer.start();

            plugin.getLogger().info("HTTP server started in port 8090");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void shutdown(){
        if (httpServer != null){
            httpServer.stop(0);

            plugin.getLogger().info("HTTP server shutdown");
        }
    }
}
