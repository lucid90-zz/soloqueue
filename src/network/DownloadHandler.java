/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network;

import entities.DownloadTaskCompliant;
import entities.Request;
import entities.Response;
import entities.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;
import javax.swing.SwingWorker;
import log.LogFile;
import log.StandardLogger;
import mediator.Mediator;

/**
 *
 * @author LucianDobre
 */
public class DownloadHandler extends SwingWorker<Object, Object>{
    Mediator med;
    User user;
    String filename;
    int partSize;
    int remoteServicingPort;
    DownloadTaskCompliant dtc = null;
    int fileSize;
    
    @Override
    protected Object doInBackground() throws Exception {
        int iter = 0 ;
        boolean listening = true;
        Request req = new Request(); req.setType(Request.GETPACKET);
        Response res = null;
        SocketChannel requestChannel = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        
        RandomAccessFile raf = new RandomAccessFile(
                getMed().getLoggedInUser().getDownloadDirectory()+
                        filename.split("\\\\")[filename.split("\\\\").length-1]+
                        ".downloaded", "rw");
        
        requestChannel = SocketChannel.open(new InetSocketAddress(user.getHostname(),remoteServicingPort));
        out = new ObjectOutputStream(requestChannel.socket().getOutputStream());
        in = new ObjectInputStream(requestChannel.socket().getInputStream());
        
        
        while ( listening ){
            try{
                out.writeObject(req);
                res = (Response) in.readObject();
                //System.out.println(res.getText().toString());

                switch ( res.getType() ){
                    case Response.OK:
                        /*Correct Request*/
                        raf.write(res.getText(),0,res.getSize());
                        iter++;
                        
                        publish((int)((partSize * 100 * iter)/fileSize));
                
                        if ( res.getSize() < partSize) 
                            listening = false;
                        break;
                }
            } catch (IOException ex) {
                StandardLogger.getLog("network").log(LogFile.SEVERITY_STD_ERROR, "Could not read from file");
            } catch (ClassNotFoundException ex) {
                StandardLogger.getLog("network").log(LogFile.SEVERITY_STD_ERROR, "Could not deserialize response in UploadHandler");
            }
        }
        raf.close();out.close();in.close();
        return null;
    }

    @Override
    protected void process(List<Object> chunks) {
        dtc.getProgressDone().setValue((int)chunks.get(0));
        System.out.println("DOWNLOAD AT: "+(int)chunks.get(0));
    }

    @Override
    protected void done() {
        dtc.getMed().removeDownloadTask(dtc);
    }
    
    

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getPartSize() {
        return partSize;
    }

    public void setPartSize(int partSize) {
        this.partSize = partSize;
    }

    public Mediator getMed() {
        return med;
    }

    public void setMed(Mediator med) {
        this.med = med;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRemoteServicingPort() {
        return remoteServicingPort;
    }

    public void setRemoteServicingPort(int remoteServicingPort) {
        this.remoteServicingPort = remoteServicingPort;
    }

    public DownloadTaskCompliant getDtc() {
        return dtc;
    }

    public void setDtc(DownloadTaskCompliant dtc) {
        this.dtc = dtc;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
    
}
