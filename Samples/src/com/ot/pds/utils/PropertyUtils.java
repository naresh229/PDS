/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ot.pds.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author nchebolu
 */
public class PropertyUtils {

	public static void saveProperty(PropertyBean bean, String filePath) {
		if (bean != null) {
			Map<String, PropertyBean> props = getProperties(filePath);
			props.put(bean.getName(), bean);
			saveProperties(filePath, props);
		}
	}

	public static void saveProperty(String key, String value, String filePath) {
		if (!Utils.isEmptyOrNull(key)) {
			PropertyBean bean = new PropertyBean();
			bean.setName(key);
			bean.setValue(value);
			saveProperty(bean, filePath);
		}
	}

	public static Map<String, PropertyBean> getProperties(String filePath) {
		System.out.println("Reading properies started.." + filePath);
		Map<String, PropertyBean> map = new LinkedHashMap<String, PropertyBean>();
		PropertyBean bean;
		File file = new File(filePath);
		if (!file.exists()) {
			System.out.println("Error:" + filePath + "Does not Exist");
		} else {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file)));
				String str;
				StringBuilder sb = new StringBuilder();
				int eqSignIndex;
				while ((str = br.readLine()) != null) {
					if (str.trim().startsWith("#") ) {
						sb.append(str);
						sb.append(System.getProperty("line.separator"));

					} else if(str.trim().equals("")){
						sb.append(System.getProperty("line.separator"));
					}
					else {
						eqSignIndex = str.indexOf('=');
						if (eqSignIndex != -1) {
							bean = new PropertyBean();
							bean.setName(str.substring(0, eqSignIndex));
							bean.setValue(str.substring(eqSignIndex + 1));
							bean.setComment(sb.toString());
							sb.setLength(0);
							map.put(bean.getName(), bean);
						}
					}
				}
				if(sb.length() > 0){
					bean = new PropertyBean();
					bean.setName("");
					bean.setValue("");
					bean.setComment(sb.toString());
					sb.setLength(0);
					map.put(bean.getName(), bean);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println(ex.getMessage());
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		return map;
	}

	public static PropertyBean getProperty(String filePath, String key){
        // Get the properties and set the values
       Map<String, PropertyBean> map = getProperties(new File(filePath).getAbsolutePath());
       PropertyBean propertyBean = map.get(key);
       
       return propertyBean;
   }
	
	private static String getPropertyString(PropertyBean bean) {
		StringBuilder sb = new StringBuilder();
		if (bean != null) {
			sb.append(bean.getComment());
			// sb.append(System.getProperty("line.separator"));
			if(!bean.getName().trim().equals("")){
				sb.append(bean.getName());
				sb.append("=");
				sb.append(bean.getValue());
				sb.append(System.getProperty("line.separator"));
			}
			
		}
		return sb.toString();
	}

	public static void saveProperties(String path, Map<String, PropertyBean> map) {
		System.out.println("Saving properites  to " + path);
		Map<String,PropertyBean> originalMap = getProperties(path);
		if(originalMap == null){
			originalMap = new LinkedHashMap<String, PropertyBean>();
		}
		originalMap.putAll(map);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path));
			Set<String> keySet = originalMap.keySet();
			PropertyBean bean;
			for (String key : keySet) {
				bean = originalMap.get(key);
				bw.write(getPropertyString(bean));
			}
			bw.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println(ex.getMessage());
			}
		}
	}
	
	public static void main(String arg[]){

		String path ="pdstest1.properties";
		Map<String,PropertyBean> testMap = getProperties(path);
		saveProperties("pdstest.properties", testMap);
	}
}
