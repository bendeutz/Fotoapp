package com.example.de.vogella.camera.api;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocket extends Socket {

    public MySocket(String ipAddress, int port) throws UnknownHostException, IOException{
        super(ipAddress,port);
    }

    public void close(){
        // do nothing
    }

    public void reallyClose() throws IOException{
        super.close();
    }
}