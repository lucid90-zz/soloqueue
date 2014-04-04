/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import entities.DownloadTask;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mediator.Mediator;

public class GUI extends JFrame implements MouseListener{
    
    private Mediator med;
    
    private JPanel jpFilesDownloads;
    private JList fileList;
    private DefaultListModel fileListModel;
    
    private JPanel jpUsers;
    private JList userList;
    private DefaultListModel userListModel;
    
    private  JTable downloadsTable;
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
        fileList.addMouseListener(this);
        
        jpUsers = new JPanel(new BorderLayout());
        jpUsers.setPreferredSize(new Dimension(100, 0));
        userListModel = new DefaultListModel();
        userList = new JList(userListModel);
        jpUsers.add(userList, BorderLayout.CENTER);
        jpUsers.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        userList.addMouseListener(this);
        
        downloadsTableModel = new DefaultTableModel(new Vector(),med.getDownloadColumnNames()){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        downloadsTable = new JTable(downloadsTableModel);
        downloadsTable.getColumnModel().getColumn(3).setCellRenderer(new JProgressBarCellRenderer());
        downloadsTable.setPreferredSize(new Dimension(0, 200));
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

    public synchronized JTable getDownloadsTable() {
        return downloadsTable;
    }

    public void setDownloadsTable(JTable downloadsTable) {
        this.downloadsTable = downloadsTable;
    }

    public synchronized DefaultTableModel getDownloadsTableModel() {
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if ( e.getSource() == userList && e.getClickCount() == 2){
            med.setFiles();
        }
        
        if ( e.getSource() == fileList && e.getClickCount() == 2){
            med.addDownloadTaskFromGUI();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
