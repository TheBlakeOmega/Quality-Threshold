package com.example.qtminer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
//Classe per la gestione statica della socket. Viene inizializzata una volta etutti i metodi sono synchronized per evitare un accesso simultaneo.
public class SocketHandler {
    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    public static synchronized ObjectOutputStream getObjectout(){
        return out;
    }
    public static synchronized ObjectInputStream getObjectin(){
        return in;
    }

    public static synchronized void setSocket(Socket s){
        socket = s;
        try {
            out=new ObjectOutputStream(s.getOutputStream());
            in=new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
