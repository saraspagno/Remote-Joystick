package com.example.joystickandroidapp.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

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
        try {
            this.mySocket = new Socket(this.ip_address, this.port);
            this.myOutputStream  = this.mySocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.out = new PrintWriter(myOutputStream, true);
    }

    public void disconnect() throws IOException {
        this.out.close();
        this.myOutputStream.close();
        this.mySocket.close();
    }
}
