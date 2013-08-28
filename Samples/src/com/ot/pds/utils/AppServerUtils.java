/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ot.pds.utils;

import java.io.*;
import java.net.ServerSocket;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author nchebolu
 */
public class AppServerUtils extends Utils {

    public static String checkAndGetAppServerDirName() throws Exception {
        Map<String, PropertyBean> map = PropertyUtils.getProperties("config.properties");
        PropertyBean propertyBean = map.get(Constants.TOMCAT_ORIGINAL_INSTANCE_NAME);
        if (propertyBean == null || isEmptyOrNull(propertyBean.getValue())) {
            System.out.println("App server directory name should be defined ");
            throw new Exception("App server directory name should be defined ");
        } else if (!new File(propertyBean.getValue()).exists()) {
            System.out.println("App server directory not found");
            throw new Exception("Tomcat Directory specified in config.properties not found:" + propertyBean.getValue());

        }
        return propertyBean.getValue();
    }

    public static void updateAppServerPorts(String vapRoot, AppServerConfigBean bean) throws Exception {
        System.out.println("Updating Server ports started:");
        String appServDirName = checkAndGetAppServerDirName();
        String serverXmlPath = vapRoot + File.separator + appServDirName
                + File.separator + "conf" + File.separator + "server.xml";
        System.out.println(serverXmlPath);
        updateServerXmlPorts(serverXmlPath, bean);
        updateServerDebuggingPort(vapRoot, bean.getDebuggingPort());
    }

    public static void updateServerDebuggingPort(String vapRoot, String debuggingPort) throws Exception {
        BufferedReader br = null;
        BufferedWriter bw = null;
        String appServDirName = checkAndGetAppServerDirName();
        String binPath = vapRoot + File.separator + appServDirName + File.separator + "bin";
        String setEnvBatPath = binPath + File.separator + "setenv.bat";
        File file = new File(setEnvBatPath);
        System.out.println("Updatting Debugging Port");
        System.out.println(setEnvBatPath);
        try {
            br = new BufferedReader(new FileReader(file));
            bw = new BufferedWriter(new FileWriter(binPath + "/temp.bat"));
            String str = Constants.EMPTY_STRING;

            while ((str = br.readLine()) != null) {
                Pattern pattern = Pattern.compile("address[\\s]*=[\\s0-9]*", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    bw.write(matcher.replaceFirst("address=" + debuggingPort + "  "));
                } else {
                    bw.write(str);
                }
            }
            bw.flush();
            System.out.println("Debugging port has been updated.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
        }
        // Delete the original file and rename the temp.bat file to setenv.bat file   
        file.delete();
        file = new File(binPath + File.separator + "temp.bat");
        file.renameTo(new File(binPath + File.separator + "setenv.bat"));
    }

    public static void updateServerXmlPorts(String serverXmlPath, AppServerConfigBean bean) {
        try {
            System.out.println("Updatting Servers Ports");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(serverXmlPath);
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile("//Server");

            // Set the Server Port
            Node serverNode = (Node) expr.evaluate(doc, XPathConstants.NODE);
            Node portAttr = serverNode.getAttributes().getNamedItem("port");
            if (portAttr == null) {
                portAttr = doc.createAttribute("port");
                serverNode.getAttributes().setNamedItem(portAttr);
            }

            portAttr.setNodeValue(bean.getServerPort());

            // Set the Http Port

            expr = xpath.compile("/Server/Service/Connector[contains(@protocol,\"HTTP\")]");
            Node connectorNode = (Node) expr.evaluate(doc, XPathConstants.NODE);
            connectorNode.getAttributes().getNamedItem("port").setNodeValue(bean.getHttpPort());
            connectorNode.getAttributes().getNamedItem("redirectPort").setNodeValue(bean.getHttpRPort());

            expr = xpath.compile("/Server/Service/Connector[contains(@protocol,\"AJP\")]");
            connectorNode = (Node) expr.evaluate(doc, XPathConstants.NODE);
            connectorNode.getAttributes().getNamedItem("port").setNodeValue(bean.getAjpPort());
            connectorNode.getAttributes().getNamedItem("redirectPort").setNodeValue(bean.getAjpRPort());

            // write the content into xml file TransformerFactory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(serverXmlPath));
            transformer.transform(source, result);

            System.out.println("Updatting Server Ports done");


        } catch (TransformerException ex) {
            ex.printStackTrace();
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    public static AppServerConfigBean getAppServerConfigBean(String appserverPath) throws Exception {
        System.out.println("Getting appserver config bean");
        String serverXmlPath = appserverPath + File.separator + "conf" + File.separator + "server.xml";
        AppServerConfigBean bean = getServerXmlPorts(serverXmlPath);
        bean.setDebuggingPort(getDebbuggingPort(appserverPath));
        System.out.println(bean.toString());
        return bean;
    }

    public static String getDebbuggingPort(String appserverPath) throws Exception {
        System.out.println("Reading Debugging Port");
        BufferedReader br = null;
        //   String appServDirName = checkAndGetAppServerDirName();
        String setEnvBatPath = appserverPath + File.separator + "bin" + File.separator + "setenv.bat";
        // String setEnvBatPath = binPath + "/setenv.bat";
        System.out.println(setEnvBatPath);
        File file = new File(setEnvBatPath);
        String debugginPort = Constants.EMPTY_STRING;
        try {
            br = new BufferedReader(new FileReader(file));
            String str = Constants.EMPTY_STRING;

            while ((str = br.readLine()) != null) {
                Pattern pattern = Pattern.compile("address[\\s]*=[\\s0-9]*", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    String temp = matcher.group();
                    debugginPort = temp.split("=")[1];
                    System.out.println("Debugging Port:" + debugginPort);
                    break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
        }
        return debugginPort.trim();
    }

    public static AppServerConfigBean getServerXmlPorts(String serverXmlPath) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        System.out.println("Reading Server xml Ports:");
        AppServerConfigBean bean = new AppServerConfigBean();
        File f = new File(serverXmlPath);
        if (!f.exists()) {
            System.out.println(serverXmlPath + "  File not found");
            throw new FileNotFoundException(serverXmlPath + "  File not found");
        } 
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(serverXmlPath);
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile("//Server");

            // Set the Server Port
            Node serverNode = (Node) expr.evaluate(doc, XPathConstants.NODE);
            Node portAttr = serverNode.getAttributes().getNamedItem("port");
            if (portAttr != null) {
                bean.setServerPort(portAttr.getNodeValue());
            }

            expr = xpath.compile("/Server/Service/Connector[contains(@protocol,\"HTTP\")]");
            Node connectorNode = (Node) expr.evaluate(doc, XPathConstants.NODE);
            bean.setHttpPort(connectorNode.getAttributes().getNamedItem("port").getNodeValue());
            bean.setHttpRPort(connectorNode.getAttributes().getNamedItem("redirectPort").getNodeValue());

            expr = xpath.compile("/Server/Service/Connector[contains(@protocol,\"AJP\")]");
            connectorNode = (Node) expr.evaluate(doc, XPathConstants.NODE);
            bean.setAjpPort(connectorNode.getAttributes().getNamedItem("port").getNodeValue());
            bean.setAjpRPort(connectorNode.getAttributes().getNamedItem("redirectPort").getNodeValue());

            System.out.println("Reading Server ports done:");

        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            throw pce;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw ioe;
        } catch (SAXException sae) {
            sae.printStackTrace();
            throw sae;
        }
    
        
        return bean;
    }

    public static void doAppServerSetup(String vapRoot, String appServerPath) throws Exception {
        System.out.println("App Server setup Started:");
        //  String appServDirName = checkAndGetAppServerDirName();
        //String appServerDirPath = vapRoot + File.separator + appServDirName;
        File file = new File(appServerPath);
        //Remove directory completely if it exists
        if (file.exists()) {
            System.out.println(appServerPath + " exists.Removing it up..");
            removeDirOrFile(file);
        }

        //Copy App Server Direcotry
        copyAppServerDir(appServerPath);

        // Modify shared loader
        setSharedLoaderProperty(vapRoot, appServerPath);

        //Copy Portal.xml file
        createPortalXmlFile(vapRoot, appServerPath);

        //Create setenv.bat file
        createSetEnvBatFile(vapRoot, appServerPath);

        // Save the app server path in vaproot
        PropertyUtils.saveProperty(Constants.PORTAL_APPSERVER_PATH, appServerPath, vapRoot + File.separator + Constants.CONFIG_FILE_WORKSPACE_SPECIFIC);
    }

    public static void createSetEnvBatFile(String vapRoot, String appServerPath) throws Exception {
        try {
            System.out.println("Creating SetEnv.bat file:");
            // String appSerDirName = checkAndGetAppServerDirName();
            String setEnvBatPath = appServerPath
                    + File.separator + "bin" + File.separator + "setenv.bat";
            File file = new File(setEnvBatPath);
            StringBuilder sb = new StringBuilder();
            sb.append("set CATALINA_OPTS=-Xmx1048M -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5007");
            sb.append(" -Dcom.vignette.portal.installdir.path=" + vapRoot.replaceAll("/", "\\\\") + "\\VAP\\portalserver ");
            PrintStream ps = new PrintStream(file);
            ps.print(sb.toString());
            ps.flush();
            ps.close();
            System.out.println("setenv.bat has been created.Path: " + file.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    public static void createPortalXmlFile(String vapRoot, String appServerPath) throws Exception {
        try {
            System.out.println("Creating portal.xml file:");
            // String appSerDirName = checkAndGetAppServerDirName();
            String localHostDir = appServerPath + File.separator
                    + "conf" + File.separator + "Catalina" + File.separator + "localhost";
            File file = new File(localHostDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(localHostDir + File.separator + "portal.xml");
            file.createNewFile();
            String docBase = vapRoot.replaceAll("\\\\", "/") + "/VAP/portalserver/portal";
            StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Context path=\"/portal\" docBase=\"" + docBase + "\" reloadable=\"false\" crossContext=\"true\">\n"
                    + "<Manager className=\"org.apache.catalina.session.StandardManager\" pathname=\"\"/>\n</Context>");
            PrintStream ps = new PrintStream(file);
            ps.print(sb.toString());
            ps.flush();
            ps.close();
            System.out.println("portal.xml file hase been created (path:" + file.getAbsolutePath() + " ) Docbase=" + docBase);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;

        }
    }

    public static void setSharedLoaderProperty(String vapRoot, String appServervDirPath) throws Exception {
        System.out.println("Settinng Shared loaded property");
        //  String appSerDirName = checkAndGetAppServerDirName();
        String catalinaPropFile = appServervDirPath
                + File.separator + "conf" + File.separator + "catalina.properties";
        Map<String, PropertyBean> map = PropertyUtils.getProperties(catalinaPropFile);
        PropertyBean propertyBean = map.get(Constants.SHARED_LOADER);
        String sharedLoader = vapRoot.replaceAll("\\\\", "/") + "/VAP/portalserver/lib/shared/*.jar";
        propertyBean.setValue(sharedLoader);
        PropertyUtils.saveProperties(catalinaPropFile, map);
    }

    public static void copyAppServerDir(String appServervDirPath) throws Exception {

        System.out.println("Copying app server directory");
        String appSerDirName = checkAndGetAppServerDirName();
        File srcFolder = new File(appSerDirName);
        File destFolder = new File(appServervDirPath);

        //make sure source exists
        if (!srcFolder.exists()) {
            System.out.println("System Server Directory does not exist in portal dev setup folder");
            // System.exit(0);

        } else {
            try {
                copyFolder(srcFolder, destFolder);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
    }

    public static boolean isPortAvailble(int port) {
        boolean portAvailble = true;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Port is not availble " + port);
            portAvailble = false;
        }
        if (server != null) {
            try {
                server.close();
            } catch (Exception ex) {
            }
        }

        return portAvailble;
    }

    public static void main(String a[]) {
        //doAppServerSetup("D:/test");
        //  updateServerDebuggingPort("d:/test", "3333333");
        //  String str = "asdfsaddress  =123";
        //   String debuggingPort = "8890";
        /*
         * if(str.matches("[a-z]*address[\\s]*=[\\s0-9]*")){
         * str.replace("address[\\s]*=[\\s0-9]*", "address=" + debuggingPort);
         * System.out.println(str); }
         */
//        createFirstAdmin();

        System.out.println("d:" + File.separator + "xyz");
        System.out.println("d:" + File.pathSeparator + "xyz");
    }
}
