/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package log;

/**
 *
 * @author LucianDobre
 */
public interface LogFile {
    public static final String SEVERITY_INFO = "SEVERITY_INFORMATION";
    public static final String SEVERITY_WARN = "SEVERITY_WARNING";
    public static final String SEVERITY_STD_ERROR = "SEVERITY_STANDARD_ERROR";
    public static final String SEVERITY_CRIT_ERROR = "SEVERITY_CRITICAL_ERROR";
    public boolean log(String info, String severity);
    public boolean init(String pathname);
}
