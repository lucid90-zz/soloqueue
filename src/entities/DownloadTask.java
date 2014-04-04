/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.util.Vector;
import javax.swing.JProgressBar;

/**
 *
 * @author LucianDobre
 */
public class DownloadTask extends Thread{
    public static final String STATE_SENDING = "send";
    public static final String STATE_RECEIVING = "receive";
    public static final String STATE_COMPLETED = "completed";
    String source;
    String destination;
    String fileName;
    JProgressBar progress;
    String downloadState;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public JProgressBar getProgress() {
        return progress;
    }

    public void setProgress(JProgressBar progress) {
        this.progress = progress;
    }

    public String getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(String downloadState) {
        this.downloadState = downloadState;
    }
    
    public Vector toVector(){
        Vector toReturn = new Vector();
        toReturn.add(source);
        toReturn.add(destination);
        toReturn.add(fileName);
        toReturn.add(progress);
        toReturn.add(downloadState);
        
        return toReturn;
    }
}
