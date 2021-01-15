package starter;

import Process.PowerManager;
import Process.Scheduler;
import Process.SerialData;
import Process.service;
import common.Settings;

public class root {
	
	public static SerialData voltagelevelreader;
	public static PowerManager powercommander;
	public static Scheduler scheduler;

	public static String args[];
	
	public static void main(String arg[]) throws Exception
	{
		try
		{
			root.args = arg;
			new service().start();
		
			root.voltagelevelreader = new SerialData(Settings.Appsettings("arduinoport"));
			root.voltagelevelreader.start();
			
			
			root.powercommander = new PowerManager();
			root.powercommander.start();
			
			Thread.sleep(60000);
			//root.scheduler = new Scheduler();
			//root.scheduler.start();
			//start Scheduler;
			
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
}
