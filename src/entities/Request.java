/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.ArrayList;
import mediator.Mediator;

/**
 *
 * @author LucianDobre
 */
public class Request implements Serializable{
    public static final int CONNECT = 0;
    public static final int GETFILE = 1;
    public static final int GETPACKET = 2;
    
    private int type;
    private User requestor;
    private ArrayList<User> users;
    private String fileName;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getRequestor() {
        return requestor;
    }

    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
}
