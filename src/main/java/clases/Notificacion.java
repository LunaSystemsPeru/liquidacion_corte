/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.awt.*;
import java.awt.event.*;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;

/**
 *
 * @author Luis
 */
public class Notificacion {

    public Notificacion() {
    }

    public void displayTray(String titulo, String texto) throws AWTException, MalformedURLException {
        // Obtener solamente una instancia del objeto SystemTray
        SystemTray tray = SystemTray.getSystemTray();

        // Si quieres crear un icono en la bandeja del sistemas como vista previa
        Image image;
        // Alternativamente (si el icono está en el directorio de la clase):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));
        image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/icons/iconfinder_Info_728979.png"));

        TrayIcon trayIcon = new TrayIcon(image, texto);
        // Deja que el sistema auto escale si es necesario
        trayIcon.setImageAutoSize(true);
        // Definir texto de tooltip (descripción emergente)
        trayIcon.setToolTip(texto);
        tray.add(trayIcon);

        trayIcon.displayMessage(titulo, texto, MessageType.INFO);
    }
}
