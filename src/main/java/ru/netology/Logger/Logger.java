package ru.netology.Logger;

import java.io.FileWriter;

public class Logger {
    private static Logger logger = null;
    private FileWriter fw;

    private Logger() {
        try {
            fw = new FileWriter("file.log", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }
    public void log(String msg) {
        try {
            fw.write(msg);
            fw.write("\n");
            fw.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
