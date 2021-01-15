package Process;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonArray;

import common.FileOperation;
import common.JSON;
import starter.root;

public class Scheduler extends Thread {

	public Scheduler()
	{
		
	}
	
	
	public void run()
	{
		String data;
		JsonArray jarray;
		try
		{
			
			while(true)
			{
				try
				{
					data = FileOperation.ReadFile("scheduler.json");
					jarray = JSON.DeserializeObject(data).getAsJsonArray();
					int ctime = Integer.parseInt(new SimpleDateFormat("HHmm").format(new Date()));
					for(int i=0;i<jarray.size();i++)
					{
						try
						{
							int ontime = jarray.get(i).getAsJsonObject().get("ontime").getAsInt();
							int offtime = jarray.get(i).getAsJsonObject().get("offtime").getAsInt();
							String switchdata = jarray.get(i).getAsJsonObject().get("switch").getAsString();
							
							if(ctime>=ontime && ctime<offtime)
							{
								root.voltagelevelreader.serialPort.writeString(switchdata.toUpperCase() + "1\n");
								PowerManager.updatestate(switchdata.toUpperCase(), "1");
								//on state
							}
							else
							{
								//off state
								root.voltagelevelreader.serialPort.writeString(switchdata.toUpperCase() + "0\n");
								PowerManager.updatestate(switchdata.toUpperCase(), "0");
							}
						}
						catch(Exception eeee)
						{
							eeee.printStackTrace();
						}
						
						//{ "switch" : "D02", "ontime" : "6:00 PM" , "offtime" : "6:00 AM" }
						//Scheduler.cdata.put(jarray.get(i).getAsJsonObject().get("switch"))
					}
				}
				catch(Exception expp)
				{
					expp.printStackTrace();
				}
				Thread.sleep(1000);
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
}
