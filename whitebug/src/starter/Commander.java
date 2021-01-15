package starter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Commander extends Thread {

    byte[] receiveData = new byte[2048];
    
    DatagramSocket serverSocket;
	public boolean status;
	public Commander() throws SocketException
	{
		status = true;
		serverSocket = new DatagramSocket(5678);
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				final InetAddress destination = InetAddress.getByName("192.168.1.110");
				DatagramPacket packet;
				String data = "123";
				if(status)
				{
					data = "90651ebea9a35ec4e018c8157492e17c";
					packet = new DatagramPacket (data.getBytes(),data.getBytes().length);
					packet.setAddress(destination);
					packet.setPort(5678);
					status = false;
				}
				else
				{
					packet = new DatagramPacket (data.getBytes(),data.getBytes().length);
					packet.setAddress(destination);
					packet.setPort(5678);
					status = false;
				}
				serverSocket.send(packet);
				Thread.sleep(10000);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
