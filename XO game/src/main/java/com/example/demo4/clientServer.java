package com.example.demo4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class clientServer extends Thread{
    Socket s;
    ObjectOutputStream outputStream ;
    ObjectInputStream inputStream ;
    String pos;
    clientServer(Socket s){
        this.s=s;
        start();
    }
    @Override
    public void run() {
        super.run();
        try {
            outputStream = new ObjectOutputStream(s.getOutputStream());
            inputStream = new ObjectInputStream(s.getInputStream());
            while (true){
                pos= (String) inputStream.readObject();
                sendPosClients();
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {

        }

    }
    void sendPosClients(){
        for(int i=0;i<Server.clientServers.size();i++) {

            if(!Server.clientServers.get(i).equals(this)) {
                try {
                    Server.clientServers.get(i).outputStream.writeObject(pos + "");
                    System.out.println(i + "hhhhhhh");
                } catch (IOException e) {

                }
            }
        }
    }
}
