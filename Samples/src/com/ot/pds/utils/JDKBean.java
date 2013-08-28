/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ot.pds.utils;

/**
 *
 * @author nchebolu
 */
public class JDKBean  {
    
    private String displayName;
    private String version;
    private String path;

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    
    public String toString(){
        
        return "Display Name: " + displayName + "\nVersion: " + version + "\nPath:" + path ;
    }
    
}
