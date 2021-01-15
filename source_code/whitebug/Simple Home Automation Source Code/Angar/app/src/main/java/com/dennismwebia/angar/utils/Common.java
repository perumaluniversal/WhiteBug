package com.dennismwebia.angar.utils;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Common
{
    public static ProgressDialog progress;

    //Staging
    public static String API_HOST="114.29.239.141";

    //Development
    //public static String API_HOST="192.168.1.107";


    public static int API_PORT=8080;
    public static String API_TOKEN="3d7e0e10b6e811e98c471d771b938041";

    public static String SendCommand(String data)
    {
        Socket client = null;
        DataInputStream in=null;
        try
        {
            data = "{ \"t\" : \"qwertyuioplkjhgfdsazxcvbnm09876543210\", \"c\" : \"ctrl\" , \"d\" : \"" + data + "\" }"  + "##END##";
            client = new Socket(InetAddress.getByName(API_HOST),API_PORT);
            client.getOutputStream().write(data.getBytes(),0,data.getBytes().length);
            in = new DataInputStream(new BufferedInputStream(client.getInputStream()));

            String resdata="";

            while(resdata.indexOf("}")==-1)
            {
                byte[] buffer = new byte[1024];
                in.read(buffer);
                String d = new String(buffer).trim();
                resdata = resdata + d;
                Thread.sleep(200);

                if(resdata.length()>300 || d.equals("null") || d==null)
                    break;
            }
            return resdata;
        }
        catch (Exception exp)
        {
            Log.d("Common",exp.getMessage());
        }
        finally
        {
            try
            {
                if(in!=null) {
                    in.close();
                    in = null;
                }
            }
            catch (Exception ee){}

            try
            {
                if(client!=null) {
                    client.close();
                    client = null;
                }
            }
            catch (Exception ee1){}
        }
        return "{ \"ErroCode\" : \"-1\"}";
    }


    public static String GetControlStatus()
    {
        Socket client = null;
        DataInputStream in=null;
        try
        {
            String data = "{ \"t\" : \"qwertyuioplkjhgfdsazxcvbnm09876543210\", \"c\" : \"status\" }"  + "##END##";
            client = new Socket(InetAddress.getByName(API_HOST),API_PORT);
            client.getOutputStream().write(data.getBytes(),0,data.getBytes().length);
            in = new DataInputStream(new BufferedInputStream(client.getInputStream()));

            String resdata="";

            Thread.sleep(1000);
            int av = in.available();
            byte[] buffer = new byte[av];
            in.read(buffer);
            String d = new String(buffer).trim();
            resdata = resdata + d;
            Thread.sleep(200);
            return resdata;
        }
        catch (Exception exp)
        {
            Log.d("Common",exp.getMessage());
        }
        finally
        {
            try
            {
                if(in!=null) {
                    in.close();
                    in = null;
                }
            }
            catch (Exception ee){}

            try
            {
                if(client!=null) {
                    client.close();
                    client = null;
                }
            }
            catch (Exception ee1){}
        }
        return "{ \"ErroCode\" : \"-1\"}";
    }


}
