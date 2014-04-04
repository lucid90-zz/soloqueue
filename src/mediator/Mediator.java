/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mediator;

import Client.WSClient;
import entities.DownloadTask;
import entities.User;
import gui.GUI;
import gui.JProgressBarCellRenderer;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JProgressBar;
import network.Network;

/**
 *
 * @author LucianDobre
 */
public class Mediator {
    private Vector<String> downloadColumnNames;
    private GUI gui;
    private Network net;
    private WSClient client;
    private ArrayList<User> users;
    private User loggedInUser;

    public Mediator() {
        downloadColumnNames = new Vector<>();
        downloadColumnNames.add("Source");
        downloadColumnNames.add("Destination");
        downloadColumnNames.add("File name");
        downloadColumnNames.add("Progress");
        downloadColumnNames.add("Status");
        users = new ArrayList<>();
    }

    public GUI getGui() {
        return gui;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public Network getNet() {
        return net;
    }

    public void setNet(Network net) {
        this.net = net;
    }

    public WSClient getClient() {
        return client;
    }

    public void setClient(WSClient client) {
        this.client = client;
    }

    public Vector<String> getDownloadColumnNames() {
        return downloadColumnNames;
    }

    public void setDownloadColumnNames(Vector<String> downloadColumnNames) {
        this.downloadColumnNames = downloadColumnNames;
    }
    
    public void addDownloadTask(DownloadTask dt){
        gui.getDownloadsTableModel().addRow(dt.toVector());
        
        /*HERE I WOULD START THE DOWNLOAD JOB*/
        dt.start();
    }
    
    public String getSelectedFilename(){
        return (String)gui.getFileListModel().get(gui.getFileList().getSelectedIndex());
    }
    
    public User getSelectedUser(){
        return users.get(gui.getUserList().getSelectedIndex());
    }
    
    /*Mediator calls this when gui announces a double-click event on a file*/
    public synchronized void addDownloadTaskFromGUI(){
        String filename = getSelectedFilename();
        User user = getSelectedUser();
        
        DownloadTask dt = new DownloadTask();
        dt.setMed(this);
        dt.setSource(user.getDisplayName());
        dt.setDestination(loggedInUser.getDisplayName());
        dt.setFileName(filename);
        dt.setProgress(new JProgressBar());
        dt.setDownloadState(DownloadTask.STATE_RECEIVING);
        
        gui.getDownloadsTableModel().addRow(dt.toVector());
      
        /*HERE I WOULD START THE DOWNLOAD JOB*/
        dt.start();
    }
    
    /*DownloadTasks call this method to remove themselves from download*/
    public synchronized void removeDownloadTask(DownloadTask dt){
        Vector data = new Vector(gui.getDownloadsTableModel().getDataVector());
        Object toRemove = null;
        for( Object v : data ){
           if ( ((Vector)v).equals(dt.toVector())){
               toRemove = v;
               break;
           }
        }
        data.removeElement(toRemove);
        gui.getDownloadsTableModel().setDataVector(data, downloadColumnNames);
        gui.getDownloadsTable().getColumnModel().getColumn(3).setCellRenderer(new JProgressBarCellRenderer());
    }
    
    public void addUser(User u){
        users.add(u);
        gui.getUserListModel().clear();
        for (User i : users){
            gui.getUserListModel().addElement(i.getDisplayName());
        }
    }
    
    public void removeUser(User u){
        gui.getUserListModel().removeElement(u.getDisplayName());
    }
    
    public void setFiles(){
        gui.getFileListModel().clear();
        for (String file : users.get(gui.getUserList().getSelectedIndex()).getFiles()){
            gui.getFileListModel().addElement(file);
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
