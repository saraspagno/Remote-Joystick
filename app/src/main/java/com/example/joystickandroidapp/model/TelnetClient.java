package com.example.joystickandroidapp.model;


import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.SocketHandler;

public class TelnetClient {
    String ip_address;
    int port;

    Socket mySocket;
    OutputStream myOutputStream;
    PrintWriter out;


    public TelnetClient(String ip_address, int port) {
        this.ip_address = ip_address;
        this.port = port;
    }

    // TODO: handle exception well also in disconnect
    public void connect() {
        //this.mySocket = new Socket(this.ip_address, this.port);
        try {
            this.mySocket = new Socket("192.168.6.63", 5400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.myOutputStream  = this.mySocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.out = new PrintWriter(myOutputStream, true);
        //Log.d("IN CONNECT:\n", "IP: "+ip_address+"\n"+"Port: "+port);
    }

    public void disconnect() throws IOException {
        this.out.close();
        this.myOutputStream.close();
        this.mySocket.close();
    }
}
