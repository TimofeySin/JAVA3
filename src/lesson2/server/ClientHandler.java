package ru.geekbrains.Jawa2.Lesson8.server;

import ru.geekbrains.Jawa2.Lesson8.server.model.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ClientHandler {

    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Client client;
    private int lastActive;

    public ClientHandler(Server server, Socket socket, Client client) {
        this.server = server;
        this.socket = socket;
        this.client = client;

        this.in = null;
        this.out = null;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setLastActive((int)System.currentTimeMillis());
            new Thread(() -> {
                try {
                    while (true) {
                        String line = in.readUTF();
                        setLastActive((int)System.currentTimeMillis());
                        if (line.startsWith("/")){
                            if (line.startsWith("/w ")){
                                String[] tokens = line.split(" ", 3);
                                server.sendTo(this, tokens[1], tokens[2]);
                            }
                            if (line.startsWith("/block ")){
                                String[] tokens = line.split(" ", 2);
                                client.getBlockList().add(tokens[1].trim());
                            }
                        }else {
                            System.out.print("from " + client.getNick() + line);
                            server.broadCast(this,client.getNick() + ": " + line);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    server.unsubscribe(this);
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLastActive(int lastActive) {
        this.lastActive = lastActive;
    }

    public int getLastActive() {
        return lastActive;
    }

    public void closeClient(){
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client getClient() {
        return client;
    }
}
