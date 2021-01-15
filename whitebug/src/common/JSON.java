package common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;


public class JSON {

	 
	public static Object Deserialize(String jsonstr,String classname)
	{
		try
		{
			return new Gson().fromJson(jsonstr, Class.forName(classname));
		}
		catch(Exception exp)
		{
			//log.error("JSON.Deserialize" + jsonstr);
			exp.printStackTrace();
		}
		return null;
	}
	
	public static JsonElement DeserializeObject(String jsonstr)
	{
		try
		{	
			return (JsonElement)new JsonParser().parse(jsonstr);
		}
		catch(Exception exp)
		{
			if(jsonstr!=null)
			   Console.println("Deserialize Error Object "+ jsonstr);
			
			exp.printStackTrace();
		}
		return null;
	}
	
	
	public static String Serialize(Object obj)
	{
		try
		{
			return new Gson().toJson(obj);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return new String("");
	}
	
	public static String SerializeWithNull(Object obj)
	{
		try
		{
		   Gson gson = new GsonBuilder().serializeNulls().create();			   
		   return  gson.toJson(obj);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return new String("");
	}
	
	public static String SerializeWithIgnore(Object obj)
	{
		try
		{
		   Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();			   
		   return  gson.toJson(obj);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return new String("");
	}
	
	public static String GetValue(String json,String key)
	{
		try
		{
			return DeserializeObject(json).getAsJsonObject().get(key).getAsString();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return new String("");
	}
	
	public static JsonObject GetObject(String json,String key)
	{
		try
		{
			return DeserializeObject(json).getAsJsonObject().getAsJsonObject(key);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return null;
	}
	
	public static JsonArray GetJsonArray(String json,String key)
	{
		try
		{
			return DeserializeObject(json).getAsJsonObject().getAsJsonArray(key);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return null;
	}
	
	
	public static String NeoSerialize(Object obj) throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			mapper.configure(Feature.QUOTE_FIELD_NAMES, false);
			return mapper.writeValueAsString(obj);
		}
		catch(Exception exp)
		{
			throw exp;
		}
	}
	
	public static Object[] GetKeypair(String json)
	{
		List<String> keys = new ArrayList<String>();
		try
		{
			Set<Entry<String, JsonElement>> flst = new JsonParser().parse(json).getAsJsonObject().entrySet();
			for (Entry<String, JsonElement> entry : flst)
				keys.add(entry.getKey() + "="+ entry.getValue().getAsString());
		}
		catch(Exception exp)
		{
			throw exp;
		}
		return keys.toArray();
	}
	
	
	
	

	
}
