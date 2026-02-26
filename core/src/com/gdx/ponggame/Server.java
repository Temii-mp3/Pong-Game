package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private Socket socket;
    private DataInputStream inputStream;

    private Runnable onClientConnected;

    public Server(int port){
        try{
            server = new ServerSocket(port);
            while(!server.isClosed()) {
                System.out.println("Server Listening on port: " + port);
            socket = server.accept();
            if(onClientConnected != null){
                Gdx.app.postRunnable(() -> onClientConnected.run());

            }
            }

            System.out.println("Server Connected ");
            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            String line ="";

            while (!line.equals("ESC")){
                line = inputStream.readUTF();
                System.out.print("Received from client: " + line);
            }

            System.out.print("Closing Connection");
            socket.close();
            inputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        if(socket.isConnected()){
            return true;
        }
        return false;
    }

    public void setOnClientConnected(Runnable listener){
        this.onClientConnected = listener;
    }

}
