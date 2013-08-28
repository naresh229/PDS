package com.ot.pds.ant.tasks;

import java.io.File;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import com.ot.pds.utils.AppServerConfigBean;
import com.ot.pds.utils.AppServerUtils;
import com.ot.pds.utils.Constants;
import com.ot.pds.utils.PropertyBean;
import com.ot.pds.utils.PropertyUtils;

public class LoadAppServerDetailsTask extends Task{
	
	private String portalRoot;
	private String pdsConfigFile;
	public String getPortalRoot() {
		return portalRoot;
	}
	public void setPortalRoot(String portalRoot) {
		this.portalRoot = portalRoot;
	}
	public String getPdsConfigFile() {
		return pdsConfigFile;
	}
	public void setPdsConfigFile(String pdsConfigFile) {
		this.pdsConfigFile = pdsConfigFile;
	}
	
	public void execute(){
		String portalConfigFile = portalRoot + File.separator + pdsConfigFile;
		File file = new File(portalConfigFile);
		if(!file.exists()){
			System.out.println("File does not exit:" + portalConfigFile + " So could not read app server details"); 
			return;
		}
		
		Project project = getProject();
		PropertyBean pBean = PropertyUtils.getProperty(portalConfigFile,Constants.PORTAL_APPSERVER_PATH);
		AppServerConfigBean bean = null;
		try{
			AppServerConfigBean bean = AppServerUtils.getAppServerConfigBean(pBean.getValue());
			System.out.println(bean);
		}catch(Exception ex){
			throw new BuildException(ex);
		}
		
		if(bean != null){
			PropertyUtils.saveProperty("", bean.getServerPort(), pBean.getValue());
			PropertyUtils.saveProperty("", bean.getServerPort(), pBean.getValue());
			PropertyUtils.saveProperty("", bean.getServerPort(), pBean.getValue());
			PropertyUtils.saveProperty("", bean.getServerPort(), pBean.getValue());
			PropertyUtils.saveProperty("", bean.getServerPort(), pBean.getValue());
			PropertyUtils.saveProperty("", bean.getServerPort(), pBean.getValue());
		}
		
		
	}
	

}
