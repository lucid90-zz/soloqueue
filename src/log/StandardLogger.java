/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package log;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author LucianDobre
 */
public class StandardLogger {
    private static HashMap<String,LogFile> logs = new HashMap<String, LogFile>();

    public HashMap<String, LogFile> getLogs() {
        return logs;
    }

    public void setLogs(HashMap<String, LogFile> logs) {
        this.logs = logs;
    }
    
    public static void addLog ( String logid, LogFile logfile ){
        logs.put(logid, logfile);
    }
    
    public static LogFile getLog ( String logid ){
        return logs.get(logid);
    }
    
    public static void removeLOg( String logid ){
        logs.remove(logid);
    }
}
