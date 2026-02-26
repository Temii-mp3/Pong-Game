package com.gdx.ponggame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {

    private Socket socket;

    private DataInputStream input;

    private DataOutputStream out;

    public Client(String address, int port){
        try{
            socket = new Socket(address, port);
            System.out.println("Connected ");

            input = new DataInputStream(System.in);

            out = new DataOutputStream(socket.getOutputStream());
            String line = "";
            while(!line.equals("ESC")){
                try{
                    line = input.readLine();
                    out.writeUTF(line);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            input.close();
            out.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
