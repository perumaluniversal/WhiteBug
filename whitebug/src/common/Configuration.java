
  /*Name: Configuration.java
  Description: Configuration Creator
  Author:
  Created: 09/20/2017
  Copyright:
*/

package common;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class Configuration {
	
	
	private  HashMap<String,String> cfg=null;
	
	private  String cfgtype=null;
	
	
	private String configfile = "app.config";
	
	public  String Appsettings(String key)
	{
		if(cfgtype==null)
		{
			cfgtype = GetfromFile("ConfigType");
			
			if(cfgtype.equals(""))
				cfgtype = "file";
		}
		
		//String cfgtype = GetfromFile("ConfigType");
		switch(cfgtype.toLowerCase())
		{
			case "cache":
				return GetfromCache(key);
			case "file":
			default:
				return GetfromFile(key);
		}
	}
	
	public void Refresh()
	{
		try
		{
			if(Appsettings("ConfigType").toLowerCase().equals("cache"))
			{
				cfg = null;
				GetfromCache("ConfigType");
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	//Added by Ajith on 09.20.2017 for custom Configuration call
	public Configuration( String Value)
	{
		configfile = SystemInfo.TransformFilepath("conf/") + Value;
	}
	//Added by Ajith on 09.20.2017  for default Configuration call
	public Configuration()
	{
		configfile = "app.config";
	}
	
	private  String GetfromCache(String key)
	{
		try
		{
			if(cfg==null)
			{
				cfg = new HashMap<String,String>();
				
				org.w3c.dom.Document doc = (org.w3c.dom.Document) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(configfile));
				NodeList childappsettings = doc.getElementsByTagName("appsettings").item(0).getChildNodes();
				for(int i=0;i<childappsettings.getLength();i++)
				{
					if(childappsettings.item(i).getNodeName().equals("add"))
						cfg.put(((Element)childappsettings.item(i)).getAttribute("key"), ((Element)childappsettings.item(i)).getAttribute("value"));
				}
			}
			
			return (cfg.get(key)==null ? "" : cfg.get(key));
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return "";
	}
	
	public  String GetfromFile(String key)
	{
		String res="";
		try
		{
			cfg = null;
			org.w3c.dom.Document doc = (org.w3c.dom.Document) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(configfile));
			NodeList childappsettings = doc.getElementsByTagName("appsettings").item(0).getChildNodes();
			for(int i=0;i<childappsettings.getLength();i++)
			{
				if(childappsettings.item(i).getNodeName().equals("add"))
					if(((Element)childappsettings.item(i)).getAttribute("key").equals(key))
					{
						res = ((Element)childappsettings.item(i)).getAttribute("value");
						break;
					}
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return res;
	}
	
	
	public  DataConnection connector(String key)
	{
		try
		{
			return new DataConnection(key,this);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return null;
	}
	
	public  DataConnection connector(String key,Boolean isconfigdata)
	{
		try
		{
			return new DataConnection(key,isconfigdata,this);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return null;
	}
	
}
