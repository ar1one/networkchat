package ru.netology.Client;

import ru.netology.Settings.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class Client {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    private Thread thread1;
    private Thread thread2;


    public Client() {
        try {
            client = new Socket("netology.homework", Settings.getPort());
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            scanner = new Scanner(System.in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {

        //поток чтения с сервера
        thread1 = new Thread(() -> {
            while (!thread1.isInterrupted()) {
                try {
                    String msg = in.readLine();
                    System.out.println(msg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //поток отправки на сервер
        thread2 = new Thread(() -> {
            while (!thread2.isInterrupted()) {
                String msgOut = scanner.nextLine();
                out.println(msgOut);
                out.flush();
                if(msgOut.equals("/exit")) {
                    this.disconnect();
                    break;
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    private void disconnect(){
        thread1.interrupt();
        thread2.interrupt();
        try{
            in.close();
            out.close();
            scanner.close();
            client.close();
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}