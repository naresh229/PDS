/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ot.pds.utils;

/**
 *
 * @author nchebolu
 */
public class JdbcDriverBean {

    private String displayName;
    private String driverClass;
    private String defaultConnectionUrl;
    private String userName;
    private String password;

    public String toString() {
        return "Display Name: " + getDisplayName() + "\nDriver Classs:" + driverClass ;
    }

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
     * @return the driverClass
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * @param driverClass the driverClass to set
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * @return the defaultConnectionUrl
     */
    public String getDefaultConnectionUrl() {
        return defaultConnectionUrl;
    }

    /**
     * @param defaultConnectionUrl the defaultConnectionUrl to set
     */
    public void setDefaultConnectionUrl(String defaultConnectionUrl) {
        this.defaultConnectionUrl = defaultConnectionUrl;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
