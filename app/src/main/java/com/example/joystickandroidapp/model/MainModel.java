package com.example.joystickandroidapp.model;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainModel extends Thread {
    TelnetClient client;
    Boolean changed = false;
    private final BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<Runnable>();


    public MainModel(String ip_address, int port) {
        this.client = new TelnetClient(ip_address, port);
        // we are connected with Flight Gear, start sending information
        Log.d("Before START", "Main model constructor before calling start\n\n");
        this.start();
    }

    @Override
    public void run() {
        client.connect();
        Log.d("HERE in run", " WE are connected\n\n");
        while(true) {
            try {
                dispatchQueue.take().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void dispatch_joystick(double aileron, double elevator) throws InterruptedException{
        dispatchQueue.put(new Runnable() {
            @Override
            public void run() {
                client.out.print("set /controls/flight/aileron " + aileron + "\r\n");
                client.out.flush();
                client.out.print("set /controls/flight/elevator " + elevator + "\r\n");
                client.out.flush();
            }
        });
    }


    public void dispatch_rudder(double rudder) throws InterruptedException{
        Log.d("RUDDER", "We are in rudder about to send! the value is:"+rudder);
        dispatchQueue.put(new Runnable() {
            @Override
            public void run() {
                Log.d("RUDDER", "We are in rudder about to send! the value is:"+rudder);
                client.out.print("set /controls/flight/rudder " + rudder + "\r\n");
                client.out.flush();
            }
        });
    }

    public void dispatch_throttle(double throttle) throws InterruptedException{
        dispatchQueue.put(new Runnable() {
            @Override
            public void run() {
                //   System.out.println("throttle dispatch is " + throttle);
                client.out.print("set /controls/engines/current-engine/throttle " + throttle + "\r\n");
                client.out.flush();
            }
        });
    }
}
