package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


 /***************************************************************************************
 * Name: SystemInfo.java   
 * Description: SystemInfo
 * Author: Perumal
 * Created: June 1, 2014
 * Copyright: 2018 Mycura Technologies. All Rights Reserved.
 * Change History: 
 *      |2014.06.01 Perumal| File Created
 *      |2018.02.15 Perumal| Duplicate Process Validation common methods implementation
 * 
***************************************************************************************/
public class SystemInfo {

	/**
	 * @return
	 */
	public static boolean isWindows()
	{
		return (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0);
	}
	
	/**
	 * @param filepath
	 * @return
	 */
	public static String TransformFilepath(String filepath)
	{
		String fnlpath = new String();
		if(isWindows())
		{
			if(filepath.indexOf("/")!=-1)
				fnlpath = filepath.replace("/", "\\");
			else
				fnlpath = filepath;
		}
		else
		{
			if(filepath.indexOf("\\")!=-1)
				fnlpath = filepath.replace("\\", "/");
			else
				fnlpath = filepath;
		}
		return fnlpath;
	}
	
	 
	
	/**
	 * @return
	 * @throws Exception
	 */
	public static JsonObject getProcessInfo() throws Exception
	{
		JsonObject jobj = new JsonObject(); 
		try
		{
			java.lang.management.RuntimeMXBean runtime = java.lang.management.ManagementFactory.getRuntimeMXBean();
			java.lang.reflect.Field jvm = runtime.getClass().getDeclaredField("jvm");
			jvm.setAccessible(true);
			sun.management.VMManagement mgmt =(sun.management.VMManagement) jvm.get(runtime);
			java.lang.reflect.Method pid_method = null;
			//pid_method.setAccessible(true);
			
			//Test Debug Code 
			/*for(int i=0;i<mgmt.getClass().getMethods().length;i++)
			{
				System.out.println("Method Name:" + mgmt.getClass().getMethods()[i].getName());
				try{
				pid_method = mgmt.getClass().getDeclaredMethod(mgmt.getClass().getMethods()[i].getName());
				pid_method.setAccessible(true);
				
				System.out.println("Result:" + pid_method.invoke(mgmt));}catch(Exception exp){
					exp.printStackTrace();
				}
				System.out.println("");
			}*/
			
			pid_method = mgmt.getClass().getDeclaredMethod("getProcessId");
			pid_method.setAccessible(true);
			int pid = (Integer) pid_method.invoke(mgmt);
			jobj.addProperty("processid", pid);
			
			pid_method = mgmt.getClass().getDeclaredMethod("getClassPath");
			pid_method.setAccessible(true);
			pid_method.invoke(mgmt);
			jobj.addProperty("processname", (String)pid_method.invoke(mgmt));
			
			//processlist(jobj.get("processname").getAsString());
			
			return jobj;
		}
		catch(Exception exp)
		{
			throw exp;
		}
	}
	
	/**
	 * @return
	 */
	public static boolean AvoidDuplicateProcesses()
	{
		try
		{
			JsonObject jobj = getProcessInfo();
			JsonArray array = processlist(jobj.get("processname").getAsString(),jobj.get("processid").getAsString());
			
			System.out.println("Existing Process Count:" + array.size());
			System.out.println("Data:" + array.toString());
			
			if(array.size()==0)
				return true;
			else
				return false;
		}
		catch(Exception exp)
		{
			return false;
		}
	}
	
	/**
	 * @param classpath
	 * @param cpid
	 * @return
	 * @throws Exception
	 */
	private static JsonArray processlist(String classpath,String cpid) throws Exception
	{
		JsonArray objarray = new JsonArray();
		try
		{
			    String line;
			    //System.out.println("Classpath:" + classpath);
			    Process p;
			    
			    if(isWindows())
			    {
				    p = Runtime.getRuntime().exec("ps -ef | grep " + classpath);
			    }
			    else
			    {
			    	p = new ProcessBuilder(new String[] {"ps", "-ef"}).start();
			    }
			    //Sample Template
			    //dev      24473 20963  0 18:02 pts/8    00:00:00 java -jar prendio_punchoutprocessing.jar
			    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			    while ((line = input.readLine()) != null) {
			    	
			    	try
			    	{
			    	JsonObject jobj = new JsonObject();
			    	//String data = "dev      24473 20963  0 18:02 pts/8    00:00:00 java -jar prendio_punchoutprocessing.jar";
			    	String data = line;
			    	String user = data.substring(0,9).trim();
			    	String pid = data.substring(9,15).trim();
			    	String tempid = data.substring(15,21).trim();
			    	String dlck = data.substring(21,24).trim();
			    	String dtime = data.substring(24,30).trim();
			    	String pts = data.substring(30,39).trim();
			    	String runningtime = data.substring(39,48).trim();
			    	String pname = data.substring(48).trim();	 
			    	//System.out.println(line);
			    	if(!cpid.trim().equals(pid) && data.indexOf("grep")!=-1)
			    	{
			    		//System.out.println(line); //<-- Parse data here.
				    	if(pname.indexOf(classpath)!=-1)
				    	{
				    		jobj.addProperty("processid", pid);
				    		jobj.addProperty("processname", pname);
				    		objarray.add(jobj);
				    	}
			    	}
			        
			    	}
			    	catch(Exception ee){
			    		ee.printStackTrace();
			    	}
			    }
			    input.close();
			    
			    //System.out.println("Result Finished");
			    
			
			return objarray;
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
	}
	
	
}
