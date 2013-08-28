/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ot.pds.utils;

/**
 *
 * @author nchebolu
 */
public class AppServerConfigBean {
    
    private String httpPort = "" ;
    private String httpRPort = "";
    private String ajpPort = "" ;
    private String ajpRPort = "";
    private String serverPort = "" ;
    private String debuggingPort = "" ;

    /**
     * @return the httpPort
     */
    public String getHttpPort() {
        return httpPort;
    }

    /**
     * @param httpPort the httpPort to set
     */
    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }

    /**
     * @return the httpRPort
     */
    public String getHttpRPort() {
        return httpRPort;
    }

    /**
     * @param httpRPort the httpRPort to set
     */
    public void setHttpRPort(String httpRPort) {
        this.httpRPort = httpRPort;
    }

    /**
     * @return the ajpPort
     */
    public String getAjpPort() {
        return ajpPort;
    }

    /**
     * @param ajpPort the ajpPort to set
     */
    public void setAjpPort(String ajpPort) {
        this.ajpPort = ajpPort;
    }

    /**
     * @return the ajpRPort
     */
    public String getAjpRPort() {
        return ajpRPort;
    }

    /**
     * @param ajpRPort the ajpRPort to set
     */
    public void setAjpRPort(String ajpRPort) {
        this.ajpRPort = ajpRPort;
    }

    /**
     * @return the serverPort
     */
    public String getServerPort() {
        return serverPort;
    }

    /**
     * @param serverPort the serverPort to set
     */
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * @return the debuggingPort
     */
    public String getDebuggingPort() {
        return debuggingPort;
    }

    /**
     * @param debuggingPort the debuggingPort to set
     */
    public void setDebuggingPort(String debuggingPort) {
        this.debuggingPort = debuggingPort;
    }
   
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App Server Config Bean\n");
        sb.append("Server port="+serverPort +"  Debugging port ="+debuggingPort);
        sb.append("\nhttp port=" + httpPort +" http redirect port=" + httpRPort);
        sb.append("\najp port=" + ajpPort +" ajp redirect port=" + ajpRPort);
        
        return sb.toString();
        
    }
}
