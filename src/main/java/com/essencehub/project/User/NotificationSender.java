package com.essencehub.project.User;

import javafx.stage.Stage;

import java.awt.*;

public class NotificationSender {

    public static void send(String title,String message) {

        if (SystemTray.isSupported()) {
            sendNotification(title, message);
        } else {
            System.out.println("SystemTray is not supported.");
        }
    }

    private static void sendNotification(String title, String message) {
        try {

            SystemTray tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().createImage("");

            TrayIcon trayIcon = new TrayIcon(image, "JavaFX Notification");
            trayIcon.setImageAutoSize(true);


            tray.add(trayIcon);


            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
