package Process;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Console;
import common.Settings;
import starter.root;

public class Issuer extends Thread {

	public Socket client;

	public Issuer(Socket client) {
		this.client = client;
	}

	public void run() {
		DataInputStream in;
		try {
			in = new DataInputStream(new BufferedInputStream(this.client.getInputStream()));
			String line = "";

			byte[] buffer = new byte[1024];
			while (line.indexOf("##END##") == -1) {
				try {
					buffer = null;
					buffer = new byte[1024];
					in.read(buffer);
					String data = new String(buffer);
					line = line + data;
					System.out.println(line);

					if (line.length() > 300 || data.equals("null") || data == null)
						break;
				} catch (IOException i) {
					System.out.println(i);
					break;
				}
				Thread.sleep(100);
			}

			line = line.trim().replace("##END##", "");
			this.processmessage(line);
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			in = null;
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client = null;
		}
	}

	public void processmessage(String message) throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(this.client.getOutputStream()));
		;
		JsonObject jobj;
		try {
			jobj = new JsonParser().parse(message).getAsJsonObject();
			if (jobj.get("t") != null && jobj.get("t").getAsString().equals(Settings.Appsettings("token"))) {
				/*
				 * { "c" : "ctrl" , "d" : "D130" } { "c" : "status" }
				 */

				switch (jobj.get("c").getAsString().toLowerCase()) {
				case "ctrl":
					System.out.println(jobj.get("d").getAsString().toUpperCase() + "\n");

					String sevalues = Settings.Appsettings("ebswitch") + "1";

					if (sevalues.equals(jobj.get("d").getAsString().toUpperCase()))
						Console.isEBOverride = true;

					sevalues = Settings.Appsettings("ebswitch") + "0";
					if (sevalues.equals(jobj.get("d").getAsString().toUpperCase()))
						Console.isEBOverride = false;

					root.voltagelevelreader.serialPort.writeString(jobj.get("d").getAsString().toUpperCase() + "\n");
					PowerManager.updatestate(jobj.get("d").getAsString().toUpperCase().substring(0, 3),
							jobj.get("d").getAsString().toUpperCase().substring(3));
					out.writeBytes("{\"ErrorCode\":\"9999\"}" + "\n");
					break;
				case "status":
					String res = Console.info_batterylevel + "||" + Console.info_fanrunning + "||"
							+ Console.info_runningmode + "||" + PowerManager.laststate.toString();
					out.writeBytes(res + "\n");
					break;
				}
			} else {
				out.writeBytes("{\"ErrorCode\":\"1111\"}" + "\r\n");
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			out.writeBytes("{\"ErrorCode\":\"1111\"}" + "\r\n");
		} finally {
			out.flush();
			out = null;
		}
	}
}
