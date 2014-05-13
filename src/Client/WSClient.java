/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import org.apache.axis.client.Service;
import javax.xml.namespace.*;
import java.util.*;
import java.net.*;
import org.apache.axis.client.Call;

/**
 *
 * @author LucianDobre
 */
public class WSClient {
            
    public void subscribe(Integer port, String subscriber, List<String> files){
        try {
            URL endpoint = new URL("http://localhost:8080/axis/Sharix.jws");
            Service service = new Service();

            // "echo" operation
            Call echoCall;
            echoCall = (Call) service.createCall();
            echoCall.setTargetEndpointAddress(endpoint);
            echoCall.setOperationName(new QName("subscribe")); // operation name

            Object[] params = new Object[] { port, subscriber, files }; // operation parameters

            System.out.println(echoCall.invoke(params));	// operation invocation
				
        } catch (Exception e) {
                e.printStackTrace();

        }
    }
    
    public void unSubscribe(String subscriber){
        try {
            URL endpoint = new URL("http://localhost:8080/axis/Sharix.jws");
            Service service = new Service();

            // "echo" operation
            Call echoCall;
            echoCall = (Call) service.createCall();
            echoCall.setTargetEndpointAddress(endpoint);
            echoCall.setOperationName(new QName("unSubscribe")); // operation name

            Object[] params = new Object[] { subscriber }; // operation parameters

            System.out.println(echoCall.invoke(params));	// operation invocation
				
        } catch (Exception e) {
                e.printStackTrace();

        }
    }
    
    public List<String> getSubscribers(){
        List<String> toReturn = null;
        try {
            URL endpoint = new URL("http://localhost:8080/axis/Sharix.jws");
            Service service = new Service();

            // "echo" operation
            Call echoCall;
            echoCall = (Call) service.createCall();
            echoCall.setTargetEndpointAddress(endpoint);
            echoCall.setOperationName(new QName("getSubscribers")); // operation name

            Object[] params = new Object[] {}; // operation parameters

            toReturn = Arrays.asList((String[]) echoCall.invoke(params));	// operation invocation
            System.out.println("Subscribers:");
            for ( String e : toReturn ){
                System.out.println(e);
            }
				
        } catch (Exception e) {
                e.printStackTrace();
        }
        return toReturn;
    }
    
    public Integer getSubscriberPort(String subscriber){
        Integer toReturn = null;
        try {
            URL endpoint = new URL("http://localhost:8080/axis/Sharix.jws");
            Service service = new Service();

            // "echo" operation
            Call echoCall;
            echoCall = (Call) service.createCall();
            echoCall.setTargetEndpointAddress(endpoint);
            echoCall.setOperationName(new QName("getSubscriberPort")); // operation name

            Object[] params = new Object[] { subscriber }; // operation parameters

            toReturn = ((Integer)echoCall.invoke(params));	// operation invocation
            System.out.println("Port for subscriber "+subscriber+" : "+toReturn);
            
        } catch (Exception e) {
                e.printStackTrace();
        }
        return toReturn;
    }
    
    public List<String> getSubscriberFiles(String subscriber){
             List<String> toReturn = null;
        try {
            URL endpoint = new URL("http://localhost:8080/axis/Sharix.jws");
            Service service = new Service();

            // "echo" operation
            Call echoCall;
            echoCall = (Call) service.createCall();
            echoCall.setTargetEndpointAddress(endpoint);
            echoCall.setOperationName(new QName("getSubscriberFiles")); // operation name

            Object[] params = new Object[] { subscriber }; // operation parameters

            toReturn = Arrays.asList((String[])echoCall.invoke(params));	// operation invocation
            System.out.println("Files for subscriber "+subscriber+" :");
            for ( String e : toReturn ){
                System.out.println(e);
            }
				
        } catch (Exception e) {
                e.printStackTrace();
        }
        return toReturn;
    }
}
