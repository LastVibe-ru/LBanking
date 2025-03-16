package com.karpen.lBanking.services;

import com.karpen.lBanking.models.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class ApiHandler implements HttpHandler {

    private JavaPlugin plugin;
    private BankService bankService;

    public ApiHandler(JavaPlugin plugin){
        this.plugin = plugin;

        bankService = new BankService(plugin);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String query =uri.getQuery();

        String response;
        int status = 200;

        String name = null;
        Integer sum = null;

        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                if (pair[0].equals("name")) {
                    name = pair.length > 1 ? pair[1] : null;
                } else if (pair[0].equals("sum")) {
                    try {
                        sum = pair.length > 1 ? Integer.parseInt(pair[1]) : null;
                    } catch (NumberFormatException e) {
                        status = 400;
                        response = "Wrong sum";
                    }
                }
            }

            if (uri.getPath().equals("/api/add") && name != null && sum != null){
                boolean stat = bankService.balancePlus(name, sum);

                if (stat){
                    response = "Done";
                    status = 200;
                } else {
                    response = "Error";
                    status = 502;
                }
            } else if (uri.getPath().equals("/api/rem") && name != null && sum != null){
                boolean stat = bankService.balanceMinus(name, sum);

                if (stat){
                    response = "Done";
                    status = 200;
                } else {
                    response = "Error";
                    status = 502;
                }
            } else if (uri.getPath().equals("/api/get") && name != null){
                User user = bankService.getUser(name);

                if (user != null){
                    status = 200;

                    response = user.getName() + " " + user.getBalance();
                } else {
                    status = 400;
                    response = "User " + name + " not found";
                }
            } else {
                status = 502;
                response = "Bad gateway";
            }
        } else {
            status = 502;
            response = "Bad gateway";
        }

        exchange.sendResponseHeaders(status, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
