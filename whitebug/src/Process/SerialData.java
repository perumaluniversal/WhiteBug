package Process;

import java.io.BufferedReader;
import java.util.Date;

import jssc.SerialPort;

public class SerialData extends Thread
{
	public String lastdata;
	public SerialPort serialPort;
	BufferedReader br = null;
	public boolean issending;
	
	
	public SerialData(String port) throws Exception
	{
		try
		{
			serialPort = new SerialPort(port);
			serialPort.openPort();//Open serial port
			serialPort.setParams(9600, 8, 1, 0);//Set params.
			issending=false;
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				try
				{
					byte[] buffer = serialPort.readBytes(130);
				    if(buffer!=null) {
				        String tempstr = new String(buffer);
				        //System.out.println(tempstr);
				        int eidx = tempstr.lastIndexOf("}");
				        int sidx = tempstr.lastIndexOf("{",eidx-50);
				        
				        lastdata = String.valueOf(new Date().getTime()) + ":" + tempstr.substring(sidx, eidx+1);
				        System.out.println(lastdata);
				    
				    	}
				}
				catch(Exception ee) {
					
					
					ee.printStackTrace();
					
					//Serial Connection Closed or Terminated. try to reconnect 
					if(ee.getMessage().contentEquals("String index out of range: -1"))
					{
						boolean status=false;
						while(!status)
						{
							System.out.println("Reconnecting Arduino...");
							try
							{
								try {
								serialPort.closePort();}
								catch(Exception eee) {}
								
								serialPort.openPort();//Open serial port
								serialPort.setParams(9600, 8, 1, 0);//Set params.
								System.out.println("Connected with Arduino");
								status = true;
							}
							catch(Exception exp1)
							{
								System.out.println("Connection Failed with Arduino.retrying..");
								status = false;
								Thread.sleep(1000);
							}
						}
					}
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