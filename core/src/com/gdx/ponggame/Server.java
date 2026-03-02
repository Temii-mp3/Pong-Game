package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    private ServerSocket server;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Runnable onClientConnected;

    public Server(int port, Runnable listener){
        try{
            this.onClientConnected = listener;
            server = new ServerSocket(port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendInfo(String string){
        if(writer != null){
            writer.println(string);
        }
    }

    public void start(){
        Thread serverThread = new Thread(() ->{
            try{
                socket = server.accept();
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                if(onClientConnected != null){
                    Gdx.app.postRunnable(() -> onClientConnected.run());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    public void listen(Consumer<String> message){
        Thread listenerThread = new Thread(() ->{
            while(reader == null) {
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            }
            try{
                String line;
                while((line = reader.readLine()) != null){
                    message.accept(line);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }
}