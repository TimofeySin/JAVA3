package ru.geekbrains.Jawa2.Lesson8.server;

import ru.geekbrains.Jawa2.Lesson8.server.impl.CsvAuthService;
import ru.geekbrains.Jawa2.Lesson8.server.task.AuthenticationTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private final int timeOut = 120;//в секундах

    public Server() {
        ServerSocket server = null;
        Socket socket = null;
        try {

            server = new ServerSocket(8080);
            System.out.println("server start");
            checkClientsTimeOut();
            while (true) {
                socket = server.accept();
                System.out.println("client connected");
                new AuthenticationTask(socket, this, new CsvAuthService()).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void broadCast(ClientHandler from, String msg) {
        for (ClientHandler handler : clients) {
            if (!handler.getClient().getBlockList().contains(from.getClient().getNick())) {
                handler.sendMsg(msg);
            }
        }
    }

    public void userList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/users ");
        for (ClientHandler clientHandler : clients) {
            sb.append(clientHandler.getClient().getNick()).append(" ");
        }
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMsg(sb.toString());
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
        userList();
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        userList();
    }

    private void checkClientsTimeOut() {
        Thread chek = new Thread(() -> {
            while (true) {
                int dateCheck = (int) System.currentTimeMillis();
                getClients().forEach(clientHandler -> {
                    if ((dateCheck - clientHandler.getLastActive()) / 1000 >= timeOut) {
                        //unsubscribe(clientHandler);
                        clientHandler.sendMsg("/Close");
                        clientHandler.closeClient();
                    }
                });
            }
        });
        chek.setDaemon(true);
        chek.start();
        try {
            chek.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public void sendTo(ClientHandler fromHandler, String toNick, String message) {
        clients.forEach(clientHandler -> {
            if (clientHandler.getClient().getNick().equals(toNick)) {
                fromHandler.sendMsg("to " + toNick + ": " + message);
                clientHandler.sendMsg("from " + fromHandler.getClient().getNick() + ": " + message);
            }
        });
    }
}
