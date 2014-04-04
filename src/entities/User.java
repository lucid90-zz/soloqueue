/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.util.Vector;

/**
 *
 * @author LucianDobre
 */
public class User {
    
    private String username;
    private String displayName;
    private String password;
    private Vector<String> files;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayname) {
        this.displayName = displayname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Vector<String> getFiles() {
        return files;
    }

    public void setFiles(Vector<String> files) {
        this.files = files;
    }
    
    
}
