/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import mediator.Mediator;

/**
 *
 * @author LucianDobre
 */
public class TestMock  extends SwingWorker<Object, Object>{
    final int FIRST_USER = 0;
    final int SECOND_USER = 1;
    final int FIRST_FILE = 2;
    final int SECOND_FILE = 3;
    final int CLOSE = 4;

    Mediator med;
    
    public TestMock(Mediator med) {
        this.med = med;
    }

    @Override
    protected Object doInBackground() throws Exception {
        Thread.sleep(1000);
        publish(FIRST_USER);
        Thread.sleep(1000);
        publish(FIRST_FILE);
        Thread.sleep(1000);
        publish(SECOND_FILE);
        Thread.sleep(1000);
        publish(SECOND_USER);
        Thread.sleep(1000);
        publish(FIRST_FILE);
        Thread.sleep(1000);
        publish(SECOND_FILE);
        Thread.sleep(13000);
        publish(CLOSE);
        return null;
    }

    @Override
    protected void done() {
    }

    @Override
    protected void process(List<Object> chunks) {
        for ( Object chunk : chunks){
            switch(((Integer)chunk)){
                case FIRST_USER:
                    med.getGui().getUserList().setSelectedIndex(0);
                    med.setFiles();
                    break;
                case SECOND_USER:
                    med.getGui().getUserList().setSelectedIndex(1);
                    med.setFiles();
                    break;
                case FIRST_FILE:
                    med.getGui().getFileList().setSelectedIndex(0);
            try {
                med.addDownloadTaskFromGUI();
            } catch (IOException ex) {
                Logger.getLogger(TestMock.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TestMock.class.getName()).log(Level.SEVERE, null, ex);
            }
                    break;
                case SECOND_FILE:
                    med.getGui().getFileList().setSelectedIndex(1);
            try {
                med.addDownloadTaskFromGUI();
            } catch (IOException ex) {
                Logger.getLogger(TestMock.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TestMock.class.getName()).log(Level.SEVERE, null, ex);
            }
                    break;
                case CLOSE:
                    med.getGui().dispose();
                    break;
                default:
                    System.out.println("BOGUS PROCESSING REQUEST");
            }
        }
    }
    
    
}
