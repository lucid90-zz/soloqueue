/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package soloq;

import entities.User;
import gui.GUI;
import java.util.Vector;
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
        
        new Test.TestMock(med).execute();
    }
    
}
