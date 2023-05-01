package ru.netology.Server;

import ru.netology.Logger.Logger;
import ru.netology.Settings.Settings;

import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
    private List<User> list = new ArrayList<>();
    private Date dateNow = new Date();
    private final SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public Server() {
        Logger logger = Logger.getInstance();
        try (ServerSocket serverSocket = new ServerSocket(Settings.getPort())) {
            logger.log("[" + formatDate.format(dateNow) + "] сервер запущен!");
            while (true) {
                User user = new User(serverSocket.accept(), this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void addToList(User user) {
        list.add(user);
    }

    public synchronized void sendToAll(String msg, Logger logger) {
        for (User user : list) {
            user.sendMsg(msg);
        }
        logger.log(msg);
    }
    public synchronized int serverOnline() {
        sendToAll("Количество пользователей онлайн: " + User.getCOUNT(), Logger.getInstance());
        return User.getCOUNT();
    }
}
