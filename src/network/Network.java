/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network;

import entities.DownloadTask;
import entities.DownloadTaskCompliant;
import entities.Request;
import entities.Response;
import entities.User;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import log.LogFile;
import log.StandardLogger;
import mediator.Mediator;

/**
 *
 * @author LucianDobre
 */
public class Network extends SwingWorker<Object, Object>{
    private int partSize = 4096;
    private Mediator med;
    private ServerSocketChannel serverSocket = null;
    private SocketChannel requestSocket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private final boolean listening = true;

    public Mediator getMed() {
        return med;
    }

    public void setMed(Mediator med) {
        this.med = med;
    }

    public ServerSocketChannel createListeningSocket( int lowerbound){
        ServerSocketChannel serverSocket = null;
        int port = lowerbound;
        
        try{
            serverSocket = ServerSocketChannel.open();
            while ( true ){
                try{
                    port++;
                    serverSocket.socket().bind(new InetSocketAddress(port));
                    break;
                }catch (IOException e) {
                    continue;
                }
            }
        } catch (IOException ex ) { return null;}
        
        return serverSocket;
    }
    
    public boolean doDownload(User remoteUser, String filename) throws IOException, ClassNotFoundException{
        SocketChannel sc = SocketChannel.open(new InetSocketAddress(remoteUser.getHostname(), remoteUser.getPort()));
        ObjectOutputStream out = new ObjectOutputStream(sc.socket().getOutputStream());
        ObjectInputStream in = new ObjectInputStream(sc.socket().getInputStream());
        
        Request req = new Request();
        req.setRequestor(getMed().getLoggedInUser());
        req.setUsers(getMed().getUsers());
        req.setFileName(filename);
        req.setType(Request.GETFILE);
        
        out.writeObject(req);
        System.out.println("Downloader told i want a file");
        Response res = (Response) in.readObject();
        System.out.println("Downloader got a response");
        
        if ( res.getType() == Response.OK ){ //Could fail if I request a file that the user doesn't have
            System.out.println("Downloader got a good response");
            DownloadHandler dh = new DownloadHandler();
            dh.setFilename(filename);
            dh.setUser(remoteUser);
            dh.setPartSize(partSize);
            dh.setMed(getMed());
            dh.setFileSize(res.getSize());
            dh.setRemoteServicingPort(res.getPort());
            
            DownloadTaskCompliant dtc = new DownloadTaskCompliant();
            dtc.setMed(getMed());
            dtc.setSource(remoteUser.getDisplayName());
            dtc.setDestination(getMed().getLoggedInUser().getDisplayName());
            dtc.setFileName(filename);
            dtc.setProgressDone(new JProgressBar());
            dtc.setDownloadState(DownloadTask.STATE_RECEIVING);        
            getMed().getGui().getDownloadsTableModel().addRow(dtc.toVector());
            dh.setDtc(dtc);
            dtc.execute();
            
            dh.execute();
            return true;
        }
        
        return false;
    }
    
    public boolean doConnect(User u) throws IOException, ClassNotFoundException{
        SocketChannel sc = SocketChannel.open(new InetSocketAddress(u.getHostname(), u.getPort()));
        ObjectOutputStream out = new ObjectOutputStream(sc.socket().getOutputStream());
        ObjectInputStream in = new ObjectInputStream(sc.socket().getInputStream());
        
        Request req = new Request();
        req.setRequestor(getMed().getLoggedInUser());
        req.setUsers(getMed().getUsers());
        req.setType(Request.CONNECT);
        
        out.writeObject(req);
        Response res = (Response) in.readObject();
        
        if ( res.getType() == Response.OK )
            return true;
        return false;
    }
    
    public boolean doConnect(String hostname, int port) throws IOException, ClassNotFoundException{
        SocketChannel sc = SocketChannel.open(new InetSocketAddress(hostname, port));
        ObjectOutputStream out = new ObjectOutputStream(sc.socket().getOutputStream());
        ObjectInputStream in = new ObjectInputStream(sc.socket().getInputStream());
        
        Request req = new Request();
        req.setRequestor(getMed().getLoggedInUser());
        req.setUsers(getMed().getUsers());
        req.setType(Request.CONNECT);
        
        out.writeObject(req);
        Response res = (Response) in.readObject();
        
        if ( res.getType() == Response.OK )
            return true;
        return false;
    }
    
    
    
    @Override
    protected Object doInBackground(){
        int iter = 0;
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(med.getLoggedInUser().getHostname(), med.getLoggedInUser().getPort()));
            
            while (listening) {
                    iter++;
                    if ( iter / 10000 > 0){
                        iter = 0 ;
                        getMed().refreshUsers();
                    }
                    requestSocket = serverSocket.accept();
                    in = new ObjectInputStream(requestSocket.socket().getInputStream());
                    out = new ObjectOutputStream(requestSocket.socket().getOutputStream());
                    Request req = (Request) in.readObject();
                    Response res = null;
                    UploadHandler uh = null;
                    
                    switch (req.getType()) {
                        case Request.CONNECT: //Shares peer data
                            for (User u : req.getUsers()) {
                                boolean good = true;
                                for ( User trackedUser : getMed().getUsers() )
                                    if ( u.getDisplayName().equals(trackedUser.getDisplayName()) ) {
                                        good = false;
                                        break;
                                    }
                                if ( good )
                                    getMed().addUser(u);
                            }
                            res = new Response();
                            res.setType(Response.OK);
                            
                            break;
                        case Request.GETFILE:
                            for ( String filename : med.getLoggedInUser().getFiles() ){
                                if ( filename.equals(req.getFileName()) ){
                                    uh = new UploadHandler();
                                    uh.setSsc( createListeningSocket(getMed().getLoggedInUser().getPort()));
                                    uh.setMed(med);
                                    uh.setFilename(filename);
                                    uh.setPartSize(partSize);
                                    DownloadTaskCompliant dtc = new DownloadTaskCompliant();
                                    dtc.setDestination(req.getRequestor().getDisplayName());
                                    dtc.setSource(getMed().getLoggedInUser().getDisplayName());
                                    dtc.setFileName(filename);
                                    dtc.setMed(med);
                                    dtc.setDownloadState(DownloadTaskCompliant.STATE_SENDING);
                                    dtc.setProgressDone(new JProgressBar());
                                    dtc.execute();
                                    uh.setDtc(dtc);
                                    med.addDownloadTask(dtc);
                                    dtc.execute();
                                    break;
                                }
                            }
                            if ( uh != null){
                                res = new Response();
                                res.setSize((int) new File(uh.getFilename()).length());
                                res.setPort(uh.getSsc().socket().getLocalPort());
                                res.setType(Response.OK);
                            } else {
                                res = new Response();
                                res.setType(Response.FAIL);
                            }
                            break;
                        default:
                            res = new Response();
                            res.setType(Response.FAIL);
                            break;
                    }
                    
                    out.writeObject(res);
                    out.flush();
                    if ( uh != null) uh.execute();
            }
        } catch (IOException ex) {
            StandardLogger.getLog("network").log(LogFile.SEVERITY_WARN, "Could not read request from socket");
        } catch (ClassNotFoundException ex) {
            StandardLogger.getLog("network").log(LogFile.SEVERITY_WARN, "Could not find REQUEST class to parse request");
        } finally {try {in.close();out.close();} catch (Exception e) {
            StandardLogger.getLog("newtwork").log(LogFile.SEVERITY_WARN,"Could not close I/O channels");
            }
        }
        return null;
    }
    
    
}
