package ru.netology.Server;

import ru.netology.Logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private Date dateNow = new Date();
    private final SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static int COUNT = 0;
    private Socket client;

    private String name;
    private PrintWriter out;
    private BufferedReader in;
    private Thread thread1;
    private Server server;
    private Logger logger = null;

    public User(Socket client, Server server) {
        logger = Logger.getInstance();
        this.server = server;
        this.client = client;
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //поток чтения
        thread1 = new Thread(() -> {
            out.println("Добро пожаловать в чат! Введите Ваше имя: ");
            try {
                name = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.println("Привет, " + name + "! Для выхода из чата введи /exit");
            dateNow = new Date();
            System.out.println(name + " присоединился к чату!");
            server.sendToAll("[" + formatDate.format(dateNow) + "] пользователь " + name + " присоединился к чату!", logger);
            ++COUNT;
            server.serverOnline();
            server.addToList(this); //добавление пользователя в список на сервере

            //поток чтения
            while (!thread1.isInterrupted()) {
                try {
                    String msg = in.readLine();
                    dateNow = new Date();
                    if (msg.equals("/exit")) {
                        this.disconnect();
                        System.out.println("[" + formatDate.format(dateNow) + "] пользователь " + name + " покинул наш уютный чат");
                        server.sendToAll("[" + formatDate.format(dateNow) + "] пользователь " + name + " покинул наш уютный чат", logger);
                        --COUNT;
                        server.serverOnline();
                        break;
                    }
                    System.out.println("[" + formatDate.format(dateNow) + "] " + this.getName() + " написал: " + msg);
                    server.sendToAll("[" + formatDate.format(dateNow) + "] " + this.getName() + " написал: " + msg, logger);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.start();
    }

    private void disconnect() {
        thread1.interrupt();
        try {
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        out.println(msg);
        out.flush();
    }

    public String getName() {
        return name;
    }

    public static int getCOUNT() {
        return COUNT;
    }
}


