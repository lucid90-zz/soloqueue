/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network;

import entities.DownloadTaskCompliant;
import entities.Request;
import entities.Response;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.ServerSocketChannel;
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
public class UploadHandler extends SwingWorker<Object, Object>{
    Mediator med;
    String filename;
    int partSize;
    ServerSocketChannel ssc = null;
    DownloadTaskCompliant dtc = null;
    
    @Override
    protected Object doInBackground() throws Exception {
        int iter = 0;
        int size;
        boolean listening = true;
        Request req = null;
        Response res = null;
        SocketChannel requestChannel = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        byte[] buffer = null;
        
        RandomAccessFile raf = new RandomAccessFile(filename, "r");
        requestChannel = ssc.accept();
        out = new ObjectOutputStream(requestChannel.socket().getOutputStream());
        in = new ObjectInputStream(requestChannel.socket().getInputStream());
        while ( listening ){
            try {
                req = (Request) in.readObject();

                switch ( req.getType() ){
                    case Request.GETPACKET:
                        /*Correct Request*/
                        buffer = new byte[partSize];
                        res = new Response();
                        res.setSize(raf.read(buffer));
                        res.setText(buffer);
                        if ( res.getSize() < partSize ) listening = false;
                        res.setType(Response.OK);
                        out.writeObject(res);
                        //System.out.println(res.getText().toString());
                        iter++;
                        publish((int)((partSize * 100 * iter)/ raf.length()));
                        break;
                    default:
                        /*Bogus Request*/
                        res = new Response();
                        res.setType(Response.FAIL);
                        out.writeObject(res);
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
        System.out.println("UPLOADING AT: "+(int)chunks.get(0));
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

    public ServerSocketChannel getSsc() {
        return ssc;
    }

    public void setSsc(ServerSocketChannel ssc) {
        this.ssc = ssc;
    }

    public DownloadTaskCompliant getDtc() {
        return dtc;
    }

    public void setDtc(DownloadTaskCompliant dtc) {
        this.dtc = dtc;
    }
}
