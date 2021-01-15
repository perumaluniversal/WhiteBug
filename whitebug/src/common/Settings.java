package common;


import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Settings {
	
	
	private static HashMap<String,String> cfg=null;
	
	private static String cfgtype=null;
	
	public static String Appsettings(String key)
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
	
	public static void Refresh()
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
	
	
	private static String GetfromCache(String key)
	{
		try
		{
			if(cfg==null)
			{
				cfg = new HashMap<String,String>();
				
				org.w3c.dom.Document doc = (org.w3c.dom.Document) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("app.config"));
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
	
	public static String GetfromFile(String key)
	{
		String res="";
		try
		{
			cfg = null;
			org.w3c.dom.Document doc = (org.w3c.dom.Document) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("app.config"));
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
	
	public Settings()
	{
		
	}
	
	public static DataConnection connector(String key)
	{
		try
		{
			return new DataConnection(key);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return null;
	}
	
	public static DataConnection connector(String key,Boolean isconfigdata)
	{
		try
		{
			return new DataConnection(key,isconfigdata);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return null;
	}
	
}

