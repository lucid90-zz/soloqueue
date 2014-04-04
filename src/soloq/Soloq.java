/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package soloq;

import entities.DownloadTask;
import entities.User;
import gui.GUI;
import java.util.Vector;
import javax.swing.JProgressBar;
import mediator.Mediator;

/**
 *
 * @author LucianDobre
 */
public class Soloq {

    public static void main(String[] args) throws InterruptedException {
        Mediator med = new Mediator();
        GUI gui = new GUI(med);
        
        med.setGui(gui);
        
        DownloadTask dt = new DownloadTask();
        dt.setMed(med);
        dt.setSource("Lucian");
        dt.setDestination("Bogdan");
        dt.setFileName("Salarii");
        dt.setProgress(new JProgressBar());
        dt.setDownloadState("DOWNLOADING");
        
        User u = new User();
        u.setDisplayName("Lucian");
        u.setUsername("lucid");
        u.setPassword("parola");
        Vector ufiles = new Vector();
        ufiles.add("Dune.txt");
        ufiles.add("RAZR.pdf");
        u.setFiles(ufiles);
        
        User me = new User();
        me.setDisplayName("Valentin");
        me.setUsername("vale");
        me.setPassword("password");
        Vector mefiles = new Vector();
        mefiles.add("Harry Potter.txt");
        mefiles.add("Introduction to C++ programming.pdf");
        me.setFiles(mefiles);
        med.setLoggedInUser(me);

        med.addUser(u);
        med.addUser(me);
        
        med.addDownloadTask(dt);
        ((JProgressBar)med.getGui().getDownloadsTable().getModel().getValueAt(0, 3)).setValue(50);
        Thread.sleep(5000);
        dt.finishState();
    }
    
}
