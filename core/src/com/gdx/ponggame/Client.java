package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class Client {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter out;
    private Runnable onServerConnected;

    public Client(String address, int port, Runnable onServerConnected){
        try{
            socket = new Socket(address, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            if(onServerConnected != null){
                Gdx.app.postRunnable(onServerConnected);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listen(Consumer<String> message){
        Thread listenerThread = new Thread(() ->{
            try{
                String line;
                while((line = input.readLine()) != null){
                    message.accept(line);
                    out.flush();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public void sendInfo(String string){
        if(out != null){
            out.println(string);
        }
    }
}