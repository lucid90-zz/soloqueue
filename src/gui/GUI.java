/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mediator.Mediator;

public class GUI extends JFrame{
    
    private Mediator med;
    
    private JPanel jpFilesDownloads;
    private JList fileList;
    private DefaultListModel fileListModel;
    
    private JPanel jpUsers;
    private JList userList;
    private DefaultListModel userListModel;
    
    private JTable downloadsTable;
    private DefaultTableModel downloadsTableModel;

    public GUI(Mediator med){
        super();
        this.med = med;

        setTitle("File Downloader");
        setSize(600, 400);
        
        jpFilesDownloads = new JPanel(new BorderLayout());
        
        fileListModel = new DefaultListModel();
        fileList = new JList(fileListModel);
        jpFilesDownloads.add(fileList, BorderLayout.NORTH);
        jpFilesDownloads.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        jpUsers = new JPanel(new BorderLayout());
        userListModel = new DefaultListModel();
        userList = new JList(userListModel);
        jpUsers.add(userList, BorderLayout.CENTER);
        jpUsers.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        downloadsTableModel = new DefaultTableModel(new Vector(),med.getDownloadColumnNames()){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        downloadsTable = new JTable(downloadsTableModel);
        downloadsTable.getColumnModel().getColumn(3).setCellRenderer(new JProgressBarCellRenderer());
        jpFilesDownloads.add(downloadsTable,BorderLayout.SOUTH);
        jpFilesDownloads.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        getContentPane().add(jpFilesDownloads, BorderLayout.CENTER);
        getContentPane().add(jpUsers, BorderLayout.EAST);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JList getFileList() {
        return fileList;
    }

    public void setFileList(JList fileList) {
        this.fileList = fileList;
    }

    public DefaultListModel getFileListModel() {
        return fileListModel;
    }

    public void setFileListModel(DefaultListModel fileListModel) {
        this.fileListModel = fileListModel;
    }

    public JList getUserList() {
        return userList;
    }

    public void setUserList(JList userList) {
        this.userList = userList;
    }

    public DefaultListModel getUserListModel() {
        return userListModel;
    }

    public void setUserListModel(DefaultListModel userListModel) {
        this.userListModel = userListModel;
    }

    public JTable getDownloadsTable() {
        return downloadsTable;
    }

    public void setDownloadsTable(JTable downloadsTable) {
        this.downloadsTable = downloadsTable;
    }

    public DefaultTableModel getDownloadsTableModel() {
        return downloadsTableModel;
    }

    public void setDownloadsTableModel(DefaultTableModel downloadsTableModel) {
        this.downloadsTableModel = downloadsTableModel;
    }
    
    public Mediator getMed() {
        return med;
    }

    public void setMed(Mediator med) {
        this.med = med;
    }
    
    
}
