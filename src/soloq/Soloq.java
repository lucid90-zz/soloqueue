/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package soloq;

import entities.DownloadTask;
import gui.GUI;
import javax.swing.JProgressBar;
import mediator.Mediator;

/**
 *
 * @author LucianDobre
 */
public class Soloq {

    public static void main(String[] args) {
        Mediator med = new Mediator();
        GUI gui = new GUI(med);
        
        med.setGui(gui);
        
        med.getGui().getFileListModel().add(0, "FIRST ROW FILES");
        med.getGui().getFileListModel().add(1, "SECOND ROW FILES");
        
        med.getGui().getUserListModel().add(0, "FIRST ROW USERS");
        med.getGui().getUserListModel().add(1, "SECOND ROW USERS");
        
        DownloadTask dt = new DownloadTask();
        dt.setSource("Lucian");
        dt.setDestination("Bogdan");
        dt.setFileName("Salarii");
        dt.setProgress(new JProgressBar());
        dt.setDownloadState("DOWNLOADING");
        
        med.getGui().getDownloadsTableModel().addRow(dt.toVector());
        ((JProgressBar)med.getGui().getDownloadsTable().getModel().getValueAt(0, 3)).setValue(50);
    }
    
}
