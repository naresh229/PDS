/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ot.pds.utils;

import java.awt.Desktop;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author nchebolu
 */
public class Utils {
    
   // protected static Printable ptt = new PrintToConsole();
    
    public static boolean isEmptyOrNull(String str) {
        return (str == null) || (str.isEmpty());
    }
    
    public static boolean isInteger(String str) {
        boolean isInteger = true;
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            isInteger = false;
        }
        return isInteger;
    }
    
    public static boolean isNotInteger(String str) {
        
        return !isInteger(str);
    }
    
    public static void copyFolder(File src, File dest)
            throws IOException {
        if (src.isDirectory()) {
            //if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdirs();
            }
            //list all the directory contents
            String files[] = src.list();
            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile, destFile);
            }
            System.out.println("Directory copied from "
                    + src + "  to " + dest);
        } else {
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            //copy the file content in bytes 
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
            System.out.println("File copied from " + src + " to " + dest);
        }
    }
    
    public static void removeDirOrFile(File src) {
        
        if (src.isDirectory()) {

            //list all the directory contents
            String files[] = src.list();
            
            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                removeDirOrFile(srcFile);
                srcFile.delete();
            }
            src.delete();
            System.out.println("Removed Directory " + src.getAbsolutePath());
            
        } else {
            src.delete();
        }
    }

     
    public static Map<String, JDKBean> getJDKs(String jdkFilepath) throws Exception {
        Map<String, JDKBean> jdks = new LinkedHashMap<String, JDKBean>();
        File f = new File(jdkFilepath);
        if (!f.exists()) {
            System.out.println("File Path" + jdkFilepath + " not found");
        } else {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(f.getAbsolutePath());
                NodeList nList = doc.getElementsByTagName("JDK");
                JDKBean bean = null;
                String displayName;
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        bean = new JDKBean();
                        displayName = eElement.getElementsByTagName("displayName").item(0).getTextContent();
                        if(displayName.isEmpty()){
                            throw new Exception("JDK Display Name is required");
                        }
                        bean.setDisplayName(displayName);
                        bean.setVersion(eElement.getElementsByTagName("version").item(0).getTextContent());
                        String path = eElement.getElementsByTagName("path").item(0).getTextContent();
                        File jdkF = new File(path);
                        if (!jdkF.exists()) {
                            System.out.println("Path does not exists" + jdkF.getAbsolutePath());
                            throw new Exception("Path does not exists" + jdkF.getAbsolutePath());
                        }
                        bean.setPath(path);
                        jdks.put(displayName, bean);
                    }
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
        
        return jdks;
    }
    
    public static Map<String, JdbcDriverBean> getJdbcDrivers(String driversFilePath) throws Exception {
        Map<String, JdbcDriverBean> drivers = new LinkedHashMap<String, JdbcDriverBean>();
        File f = new File(driversFilePath);
        if (!f.exists()) {
            System.out.println("File Path" + driversFilePath + " not found");
        } else {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(f.getAbsolutePath());
                NodeList nList = doc.getElementsByTagName("driver");
                JdbcDriverBean bean = null;
                String driverClass;
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        bean = new JdbcDriverBean();
                  //      displayName = eElement.getElementsByTagName("displayName").item(0).getTextContent();
                        driverClass = eElement.getElementsByTagName("driverClass").item(0).getTextContent();
                        if(driverClass.isEmpty()){
                            throw new Exception("Driver Display Name is required");
                        }
                //        bean.setDisplayName(displayName);
                        bean.setDriverClass(driverClass);
                        bean.setDefaultConnectionUrl(eElement.getElementsByTagName("defaultConnectionUrl").item(0).getTextContent());
                        //bean.setUserName(eElement.getElementsByTagName("userName").item(0).getTextContent());
                        //bean.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());
                        drivers.put(driverClass, bean);
                    }
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
     
        return drivers;
    }
    
    
    public static void main(String a[]) throws IOException {
//        String path = "D:\\nchebolu.vap-feature-8.3.all-copy\\VAP\\portalserver\\config\\properties.txt";
//        Map<String, PropertyBean> map = getProperties(path);
//        Set<String> set = map.keySet();
//        for (String key : set) {
//            PropertyBean bean = map.get(key);
//            System.out.println(bean.getName() + "=" + bean.getValue());
//        }
//        path = "D:\\nchebolu.vap-feature-8.3.all-copy\\VAP\\portalserver\\config\\properties1.txt";
//        saveProperties(path, map);
        //    File f = new File(Properties.getProperty(Constants.JDK_15_PATH_KEY));
        //  System.out.println(f.getAbsolutePath());
/*
         * ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "build.bat");
         * Map<String, String> env = pb.environment(); File f = new
         * File(Properties.getProperty(Constants.JDK_15_PATH_KEY));
         * env.put("JAVA_HOME", f.getAbsolutePath());
         * System.out.println("JAVA_HOME -- " + env.get("JAVA_HOME"));
         *
         * System.out.println("PATH -- " + env.get("Path"));
         */
//        StringBuffer sb = new StringBuffer();
//        sb.append("#testteeeeeeeesadf");
//        sb.append(System.getProperty("line.separator"));
//        int index = sb.lastIndexOf(System.getProperty("line.separator"));
//        sb.append("testin");
//        if (index >= 0) {
//            //  sb.delete(index, index + System.getProperty("line.separator").length());
//        }
//        
//        System.out.print(sb.toString() + System.getProperty("line.separator").length());
//      
          Desktop desktop = Desktop.getDesktop();
        File dirToOpen = new File("d:\\");
        desktop.open(dirToOpen);
    }
}
