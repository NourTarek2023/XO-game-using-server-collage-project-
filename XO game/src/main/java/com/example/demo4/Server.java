package com.example.demo4;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    private static final int PORT = 1024;
    private static final String HOST = "localhost";
    static private ServerSocket serverSocket;
    static ArrayList<clientServer>clientServers=new ArrayList<>();
    public Server() {

    }

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                // Create server socket
//                Socket clientSocket = serverSocket.accept();
                clientServers.add(new clientServer(serverSocket.accept()));
            }

        } catch (IOException ex) {
//            throw new RuntimeException(ex);
        }
    }



}
