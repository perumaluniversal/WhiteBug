package Process;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import common.Console;
import common.FileOperation;
import common.JSON;
import common.Settings;
import starter.root;

public class PowerManager extends Thread {

	public int flag;

	public static JsonObject laststate;

	public PowerManager() {
		try {
			if (PowerManager.laststate == null) {
				String ldata = FileOperation.ReadFile("laststate.json");

				if (ldata == null || ldata.equals(""))
					ldata = "{}";
				PowerManager.laststate = JSON.DeserializeObject(ldata).getAsJsonObject();
			}

			enablelaststate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updatestate(String switchno, String value) {
		try {
			PowerManager.laststate.addProperty(switchno, value);
			PowerManager.updatestatusfile();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public static void updatestatusfile() {
		try {
			if(PowerManager.laststate==null)
				PowerManager.laststate = new JsonObject();
			
			if (!PowerManager.laststate.toString().equals("{}")) {
				FileOperation.DeleteFile("laststate.json");
				FileOperation.FileWrite("laststate.json", PowerManager.laststate.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enablelaststate() {
		try {
			Thread.sleep(15000);
			Set<Map.Entry<String, JsonElement>> entries = PowerManager.laststate.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {

				String data = entry.getKey() + PowerManager.laststate.get(entry.getKey()).getAsString();
				Console.println("Switching " + data);
				root.voltagelevelreader.serialPort.writeString(data + "\n");
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				// root.voltagelevelreader.lastdata =
				// "1574051958619:{\"0\":0.00,\"1\":0.00,\"2\":0.00,\"3\":73.45,\"4\":15.75,\"5\":89.30}";
				managetemperature();
				managecharging();

				Thread.sleep(Integer.parseInt(Settings.Appsettings("powermanagerinterval")));
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public void managetemperature() throws Exception {
		/*
		 * Temperature conditions 1. First Check for Temperature for every 20 seconds 2.
		 * if the temperature is greater than 42 then start the fan for every 20
		 * seconds. 3. if the temperature is lesser than 40 then stop the fan for every
		 * 20 seconds.
		 */
		try {

			String[] cmd = { "/opt/vc/bin/vcgencmd", "measure_temp" };
			String temperature = Console.readterminal(cmd);
			// String temperature = "temp=52'C";
			temperature = temperature.replaceAll("temp=", "").replaceAll("null", "").replaceAll("'C", "");
			Console.println("temp:" + temperature);
			// Term
			String starttemp = Settings.Appsettings("temperatureratio").split(":")[0];
			String endtemp = Settings.Appsettings("temperatureratio").split(":")[1];

			if (Double.parseDouble(temperature) > Double.parseDouble(starttemp)) {
				Console.info_fanrunning = String.valueOf(temperature) + "'C(cooling)";
				Console.println("fan is running");
				root.voltagelevelreader.serialPort.writeString(Settings.Appsettings("coolerfanswitch") + "1\n");
				PowerManager.updatestate(Settings.Appsettings("coolerfanswitch"), "1");
			} else if (Double.parseDouble(temperature) < Double.parseDouble(endtemp)) {
				Console.info_fanrunning = String.valueOf(temperature) + "'C(thersold)";
				Console.println("fan is not running");
				root.voltagelevelreader.serialPort.writeString(Settings.Appsettings("coolerfanswitch") + "0\n");
				PowerManager.updatestate(Settings.Appsettings("coolerfanswitch"), "0");
			}

			Thread.sleep(500);

		} catch (Exception exp) {

			exp.printStackTrace();
		}
	}

	public void managecharging() throws Exception {
		JsonObject jobj;
		try {
			String data = root.voltagelevelreader.lastdata.substring(root.voltagelevelreader.lastdata.indexOf("{"));
			jobj = JSON.DeserializeObject(data).getAsJsonObject();

			Double startcharge = Double.parseDouble(Settings.Appsettings("batteryratio").split(":")[0]);
			Double endcharge = Double.parseDouble(Settings.Appsettings("batteryratio").split(":")[1]);

			/*
			 * 89.64 - 13.8 15.55 - 14.5 73.60 - 13.9
			 * 
			 * 6.495652173913043 1.072413793103448 5.294964028776978
			 */

			// Battery Level
			Double clevel = jobj.get("0").getAsDouble()
					- Double.parseDouble(Settings.Appsettings("batterycalibration"));
			Console.info_batterylevel = String.valueOf(clevel);
			// 11.0:12.6
			Console.println("Current Battery Level:" + clevel);
			if (clevel < startcharge) {
				Console.info_runningmode = "MAIN";
				root.voltagelevelreader.serialPort.writeString(Settings.Appsettings("ebswitch") + "1\n");
				PowerManager.updatestate(Settings.Appsettings("ebswitch"), "1");
				Console.println("Battery is charging");
			} else if (clevel >= endcharge) {
				if (!Console.isEBOverride) {
					root.voltagelevelreader.serialPort.writeString(Settings.Appsettings("ebswitch") + "0\n");
					PowerManager.updatestate(Settings.Appsettings("ebswitch"), "0");
					Console.info_runningmode = "SOLAR";
					Console.println("Battery charging not required");
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

}
