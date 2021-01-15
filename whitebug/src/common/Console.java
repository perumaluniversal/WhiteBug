package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console 
{
	public static String info_fanrunning="-";
	public static String info_batterylevel="-";
	public static String info_runningmode="-";
	public static boolean isEBOverride;
	
	
	public static void print(String message)
	{
		if(Settings.Appsettings("mode").equals("") || Settings.Appsettings("mode").toLowerCase().equals("debug"))
		System.out.print(message);
	}
	
	public static void println(String message)
	{
		if(Settings.Appsettings("mode").equals("") || Settings.Appsettings("mode").toLowerCase().equals("debug"))
		System.out.println(message);
	}
	
	public static String readterminal(String[] commands)
	{
		Runtime rt = null;
		Process proc = null;
		BufferedReader stdInput = null;
		BufferedReader stdError = null;
		String Response="",ErrorMessage="";
		try
		{
			rt = Runtime.getRuntime();
			//String[] commands = {"/opt/vc/bin/vcgencmd", "measure_temp"};
			proc = rt.exec(commands);

			stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			// Read the output from the command
			Response = "";
			String linedata="";
			while ((linedata = stdInput.readLine()) != null) {
				Response = Response + linedata;
			}

			// Read any errors from the attempted command
			linedata="";
			ErrorMessage = "";
			while ((linedata = stdError.readLine()) != null) {
				ErrorMessage = ErrorMessage + linedata;
			}
			
			if(ErrorMessage!=null && !ErrorMessage.equals(""))
			System.out.println("ErrorMessage:" + ErrorMessage);
			
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		finally
		{
			try {
				stdError.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stdError = null;
			
			try {
				stdInput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stdInput = null;
			
			proc.destroy();
			proc = null;
			rt = null;
		}
		return Response;
	}
	
	
}
