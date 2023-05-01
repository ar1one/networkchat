package ru.netology.Settings;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Settings {

    private static final String source = "settings.txt";
    private static String settings = null;

    public static int getPort() {
        //reading settings
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            settings = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return Integer.parseInt(settings.substring(6));
    }
}
