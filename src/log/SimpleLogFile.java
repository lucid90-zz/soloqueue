/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author LucianDobre
 */
public class SimpleLogFile implements LogFile{
    
    File logRepo;
    
    @Override
    public boolean log(String info, String severity) {
        RandomAccessFile raf = null;
        try {
             raf = new RandomAccessFile(logRepo, "rw");
        } catch (FileNotFoundException ex) {
            return false; //Passing logfile issues to user
        }
        
        try {
            raf.writeChars("[LOG LEVEL: " +severity+ "] ->Info: " +info+ "\n");
        } catch (IOException ex) {
            return false; //Passing logfile issues to user
        }
        
        return true;
    }

    @Override
    public boolean init(String pathname) {
        logRepo = new File(pathname);
        try {
            logRepo.createNewFile();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
}
