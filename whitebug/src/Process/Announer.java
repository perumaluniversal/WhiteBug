package Process;

import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Hashtable;

import com.google.gson.JsonObject;
import com.mongodb.util.JSON;

public class Announer extends Thread {
	
	public DatagramSocket serverSocket;
	public DatagramPacket receivePacket;
	public byte[] receiveData = new byte[2048];
	public ByteArrayInputStream baiss;
	public static Hashtable<String, String> activeresponders;
	
	public Announer() throws SocketException
	{
		serverSocket = new DatagramSocket(2525);
		activeresponders = new Hashtable<String, String>();
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				receivePacket = new DatagramPacket(receiveData,receiveData.length);
				baiss = new ByteArrayInputStream(receivePacket.getData());
				serverSocket.receive(receivePacket);//
				
				try
				{
					 String[] data = new String(receivePacket.getData()).split("||");
					 //0 - DeviceID, 1 - IP Address
					 //data[0]
					 
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
				
				Thread.sleep(1000);
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
	public String getlist()
	{
		return JSON.serialize(activeresponders);
	}
	
	
}
