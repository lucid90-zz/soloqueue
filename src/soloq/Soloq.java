/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package soloq;

import entities.User;
import gui.GUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;
import log.LogFile;
import log.SimpleLogFile;
import log.StandardLogger;
import mediator.Mediator;

/**
 *
 * @author LucianDobre
 */
public class Soloq {

    public static void main(String[] args) {
        SimpleLogFile mediatorLogFile = new SimpleLogFile();
        mediatorLogFile.init("mediator.log");
        StandardLogger.addLog("mediator", mediatorLogFile);
        
        SimpleLogFile networkLogFile = new SimpleLogFile();
        networkLogFile.init("network.log");
        StandardLogger.addLog("network", networkLogFile);
        
        SimpleLogFile guiLogFile = new SimpleLogFile();
        guiLogFile.init("gui.log");
        StandardLogger.addLog("gui", guiLogFile);
        
        SimpleLogFile applicationLogFile = new SimpleLogFile();
        applicationLogFile.init("application.log");
        StandardLogger.addLog("application", applicationLogFile);
        
        try {
             Mediator med = new Mediator();
             
             GUI gui = new GUI(med);
             med.setGui(gui);
             
             network.Network net = new network.Network();
             net.setMed(med);
             net.execute();
             med.setNet(net);
             
             /*Read about which users exist*/
             String loggedInUser = args[0];
             med.getGui().setTitle(loggedInUser);
             User u = new User();
                
            /*Read their metadata*/
            RandomAccessFile userMeta = new RandomAccessFile("config/"+loggedInUser+"/_meta","r");

            u.setDownloadDirectory("config/"+loggedInUser+"/");
            u.setHostname("localhost");

            u.setDisplayName(userMeta.readLine());
            u.setUsername(loggedInUser);
            u.setPassword(userMeta.readLine());
            u.setHostname(userMeta.readLine());
            u.setPort(Integer.parseInt(userMeta.readLine()));

            /*Add their files*/
            Vector ufiles = new Vector();
            File dir= new File("config/"+loggedInUser+"/"), iter;

            for ( String s : dir.list() ){
                iter = new File("config/"+loggedInUser+"/"+s);
                if ( !s.startsWith("_") && iter.isFile() )
                    ufiles.add(iter.getPath());
            }
            u.setFiles(ufiles);
            med.addUser(u);
            med.setLoggedInUser(u);
         
            med.getNet().doConnect("localhost",Integer.parseInt(args[1]));
            med.getNet().doConnect("localhost",Integer.parseInt(args[2]));
        } catch (FileNotFoundException ex) {
            StandardLogger.getLog("application").log(LogFile.SEVERITY_CRIT_ERROR, "Could not open user configuration file");
        } catch (IOException ioex) {
            StandardLogger.getLog("application").log(LogFile.SEVERITY_CRIT_ERROR, "Could not read user configuration file");
        } catch (ClassNotFoundException ex) {
            StandardLogger.getLog("network").log(LogFile.SEVERITY_CRIT_ERROR, "Could not find class to deserialize response");
        }
    }
    
}
