/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ot.pds.utils;

/**
 *
 * @author nchebolu
 */
public interface Constants {

    String EMPTY_STRING = "";
    
    /** ERROR MESSAGES FOR APP SERVER DETAILS */
    String ROOTDIR_INVALID_MSG = "Please provide valid Root Directory";
    String APPSERVER_DIR_INVALID_MSG = "Please provide valid Directory for Application Server";
    String SERVER_PORT_VALID_MSG = "Please provide valid Server Port";
    String DEBUGGING_PORT_VALID_MSG = "Please provide valid Debugging Port";
    String HTTP_PORT_VALID_MSG = "Please provide valid HTTP Port";
    String HTTP_REDIRECT_PORT_VALID_MSG = "Please provide valid HTTP Redirect Port";
    String AJP_PORT_VALID_MSG = "Please provide valid AJP Port";
    String AJP_REDIRECT_PORT_VALID_MSG = "Please provide valid AJP Redirect Port";
    
    /** Error Messgaes for DB Details */
    
    String JDBC_URL_EMPTY_MSG = "Please provide Connection URL";
    String USERNAME_EMPTY_MSG = "Please provide User Name ";
    String PASSWORD_EMPTY_MSG = "Please provide Password";
    String CONFIRM_PASSWORD_EMPTY_MSG = "Please provide Confirm Password";
    String PASSWORDS_NOT_MATCHED_MSG = "Password and Confirm Password does not match";
    
    
    /** DB KEYS USED IN PROPERTIES.TXT FILE */
    String DEFAULT_DB_USER = "default.db.user";
    String DEFAUL_DB_PASSWORD = "default.db.password";
    String DEFAULT_DBDRIVER = "default.db.driver";
    String DEFAULT_DB_URL = "default.db.url";
    
    /** DB DRIVER CLASSES */
    String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    String SQL_DRIVER ="com.vignette.jdbc.sqlserver.SQLServerDriver";
    
    /** AND CONNECTION STRING FORMATS */
    String ORACLE_CONNECTION_URL_FORMAT = "jdbc:oracle:thin:@<host>:1521:<SID>";
    String SQL_CONNECTION_URL_FORMAT = "jdbc:vignette:sqlserver://<host>:1433;databasename=<database>;DescribeParameters=describeIfString";
    
    /** Confirmation messages */
    String CONNECTION_UNSUCCESSFUL_UPDATE = "Connection is unsuccessul. Do you want to update?" ;
    String CONNECTION_UNSUCCESSFUL_DO_CMPLT_SETUP = "Database Connection unsuccessful.\n Please provide valid details to continue." ;
    
    /** Style Names **/
    String STYLE_REGULAR = "regular";
    String STYLE_BOLD = "bold";
    String STYLE_ITALIC = "italic";
    String STYLE_SMALL = "small";
    String STYLE_LARGE = "large";
    String STYLE_SUCCESS ="success";
    String STYLE_FAILURE = "fail";
    String STYLE_MSG ="msgStyle";
    
    //JDK Version
    
    String JDK_15 = "JDK5";
    String JDK_16 = "JDK6";
    
    String JDK_15_PATH_KEY = "JDK.15.path";
    String JDK_16_PATH_KEY = "JDK.16.path";;
    
    //Tomcat Dir Name
    String TOMCAT_ORIGINAL_INSTANCE_NAME="pds.tomcat.fresh.instance.dir";
    
    String FAILED = "failed";
    String PORTAL_APPSERVER_PATH = "portal.appserver.path";
    
    // Developer build
    String DEVELOPER_PROPERTY_KEY ="com.vignette.portal.developer";
    
    //shared loader property
    String SHARED_LOADER = "shared.loader" ;
    
    String 
    
    //
    String JDK_VERSION_KEY="jdk.version";
    String ROOT_DIR_PATH_KEY="root.dir.path";
    String TARGET_NAME_KEY="target.name";
    
    
    // All configuration file names goes here
    String JDKS_LIST_FILE = "JDKList.xml";
    String JDBC_DRIVERS_LIST_FILE = "JdbcDrivers.xml";
    String CONFIG_FILE_WORKSPACE_SPECIFIC = "portal-dev-Setup-config";
    
    String PDS_PROPERTIES_FILE="pds.propertis.file";
   
 }
;