/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Vector;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import mediator.Mediator;

/**
 *
 * @author LucianDobre
 */
public class DownloadTaskCompliant extends SwingWorker<Object, Object>{
      public static final String STATE_SENDING = "UPLOADING";
    public static final String STATE_RECEIVING = "DOWNLOADING";
    public static final String STATE_COMPLETED = "FINISHED";
    Mediator med;
    String source;
    String destination;
    String fileName;
    JProgressBar progressDone;
    String downloadState;

    public DownloadTaskCompliant() {
        progressDone = new JProgressBar(0,100);
    }
    

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

    public JProgressBar getProgressDone() {
        return progressDone;
    }

    public void setProgressDone(JProgressBar progress) {
        this.progressDone = progress;
    }

    public String getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(String downloadState) {
        this.downloadState = downloadState;
    }

    public Mediator getMed() {
        return med;
    }

    public void setMed(Mediator med) {
        this.med = med;
    }
    
    public void finishState(){
        med.removeDownloadTask(this);
    }
    
    public Vector toVector(){
        Vector toReturn = new Vector();
        toReturn.add(source);
        toReturn.add(destination);
        toReturn.add(fileName);
        toReturn.add(progressDone);
        toReturn.add(downloadState);
        
        return toReturn;
    }

    @Override
    protected Object doInBackground() throws Exception {
        
        for ( int i = 0 ; i < 1000 ; ++i){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
            setProgress(i/10);
            publish(i/10);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
        return null;
    }
    
    @Override
    protected void done(){
     med.removeDownloadTask(this);   
    }

    @Override
    protected void process(List<Object> chunks) {
        progressDone.setValue((int)chunks.get(0));
        med.getGui().getDownloadsTable().repaint();
        
    }
    
    
}
