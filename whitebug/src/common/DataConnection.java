package common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataConnection {

		
		//Configuration Settings =new Configuration();
	
		public String url;
		public String collection;
		public String entity;
		public String ipaddress; 
		public String portno;
		public String database;
		public String username;
		public String password;
		public String type;
		
		public int contype;
		public Configuration configdata;
		
		public DataConnection()
		{
			contype=0;
		}
		
		public DataConnection(Configuration Settings)
		{
			contype=1;
			this.configdata = Settings;
		}
		
		private void invoke(String key,boolean isconfigdata)
		{
			try
			{
				 JsonObject config = null;
				 if(!isconfigdata)
				 {
					 switch(contype)
					 {
					 case 1:
						 config = new JsonParser().parse(configdata.Appsettings(key)).getAsJsonObject();
						 break;
					 case 0:
					 default:
						 config = new JsonParser().parse(Settings.Appsettings(key)).getAsJsonObject();
						 break;
						 
					 }
				 }
				 else 
					 config = new JsonParser().parse(key).getAsJsonObject();
				 
				 if(config.has("url"))
				 this.url = config.get("url").getAsString();
				 
				 if(config.has("collection"))
					 this.collection = config.get("collection").getAsString();
				 
				 if(config.has("entity"))
					 this.entity = config.get("entity").getAsString();
				 
				 if(config.has("ipaddress"))
					 this.ipaddress = config.get("ipaddress").getAsString();
				 
				 if(config.has("portno"))
					 this.portno = config.get("portno").getAsString();
				 
				 if(config.has("database"))
					 this.database = config.get("database").getAsString();
				 
				 if(config.has("username"))
					 this.username = config.get("username").getAsString();
				 
				 if(config.has("password"))
					 this.password = config.get("password").getAsString();
				 
				 if(config.has("type"))
					 this.type = config.get("type").getAsString();
				 
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
		}
		
		public DataConnection(String key,boolean isconfigdata)
		{
			this.contype=0;
			invoke(key,isconfigdata);
		}
		
		
		public DataConnection(String key,boolean isconfigdata,Configuration settings)
		{
			this.contype =1;
			this.configdata = settings;
			invoke(key,isconfigdata);
		}
		
		public DataConnection(String key)
		{
			this.contype=0;
			invoke(key,false);
		}
		
		public DataConnection(String key,Configuration settings)
		{
			this.contype =1;
			this.configdata = settings;
			invoke(key,false);
		}
		
		
		public String PrepareGet(String id)
		{
			try
			{
				return this.url + this.collection + "/" + this.entity + "/" + id;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
				//Logging.writeLog("connection", exp);
			}
			return "";
		}
		
		public String PrepareSave(String id)
		{
			try
			{
				return this.url + this.collection + "/" + this.entity + "/" + id;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return "";
		}
		
		public String PrepareUpdate(String id)
		{
			try
			{
				return this.url + this.collection + "/" + this.entity + "/" + id + "/_update";
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return "";
		}
		
		
		
		public String PrepareSearch()
		{
			try
			{
				return this.url + this.collection + "/_search?pretty=true";
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return "";
		}
		
		public String PrepareCount()
		{
			try
			{
				return this.url + this.collection + "/" + this.entity +  "/_count";
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return "";
		}
		
		
		public String PrepareDelete()
		{
			try
			{
				return this.url + this.collection + "/" + this.entity;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return "";
		}
		
		public String PrepareDeleteCollection()
		{
			try
			{
				return this.url + this.collection;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return "";
		}
		
		public String PrepareMSearch()
		{
			try
			{
				return this.url + this.collection + "/_msearch";
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return "";
		}
		
		public String PrepareBulk()
		{
			try
			{
				return this.url + this.collection + "/_bulk";
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return "";
		}
		
}