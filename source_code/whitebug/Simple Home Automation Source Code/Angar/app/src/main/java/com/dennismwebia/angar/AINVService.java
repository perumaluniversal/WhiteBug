package com.dennismwebia.angar;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.dennismwebia.angar.activities.LoginActivity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AINVService extends Service {
    public AINVService() {


        Intent notificationIntent = new Intent(this, AINVService.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0,
                notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification notification=new Notification.Builder(this)
                .setContentText("Running")
                .setContentIntent(pendingIntent).build();

        startForeground(1,notification);


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "WB sleeping", Toast.LENGTH_LONG).show();
        startAccepter();
        return START_STICKY;
    }

    public void startAccepter() {
        Thread streamThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    DatagramSocket socket = new DatagramSocket(5678);
                    Log.d("VSC", "Socket Created");

                    byte[] buffer = new byte[1024];

                    DatagramPacket packet;
                    while(true) {

                        //putting buffer in the packet
                        packet = new DatagramPacket(buffer,buffer.length);  //(buffer,buffer.length,destination,port);
                        socket.receive(packet);
                        System.out.println();
                        String res = new String(packet.getData());

                        if(res.equals("90651ebea9a35ec4e018c8157492e17c"))
                            startService(new Intent(getApplicationContext(), TransmitService.class));
                        else
                            stopService(new Intent(getApplicationContext(), TransmitService.class));

                        Thread.sleep(1000);
                    }
                } catch(Exception e) {
                    Log.e("VSC", e.getMessage());
                }
            }

        });
        streamThread.start();
    }
}
