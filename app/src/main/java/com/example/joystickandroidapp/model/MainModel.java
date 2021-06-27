package com.example.joystickandroidapp.model;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainModel extends Thread {
    TelnetClient client;

    private final BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<Runnable>();

    public MainModel(String ip_address, int port) {
        this.client = new TelnetClient(ip_address, port);
        this.start();
    }

    @Override
    public void run() {
        client.connect();
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
        dispatchQueue.put(new Runnable() {
            @Override
            public void run() {
                client.out.print("set /controls/flight/rudder " + rudder + "\r\n");
                client.out.flush();
            }
        });
    }

    public void dispatch_throttle(double throttle) throws InterruptedException{
        dispatchQueue.put(new Runnable() {
            @Override
            public void run() {
                client.out.print("set /controls/engines/current-engine/throttle " + throttle + "\r\n");
                client.out.flush();
            }
        });
    }


    public void disconnect() {
        try {
            this.client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
