package Process;

import java.net.ServerSocket;
import java.net.Socket;

import common.Settings;

public class service extends Thread {

	private Socket          socket   = null; 
    private ServerSocket    server   = null; 

    public service() throws Exception
	{
    	server = new ServerSocket (Integer.parseInt(Settings.Appsettings("broadcastportno"))); 
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				socket = server.accept(); 
				new Issuer(socket).start();
				Thread.sleep(200);	
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		
	}
	
	
}
