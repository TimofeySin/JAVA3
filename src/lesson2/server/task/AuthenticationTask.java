package ru.geekbrains.Jawa2.Lesson8.server.task;

import ru.geekbrains.Jawa2.Lesson8.server.ClientHandler;
import ru.geekbrains.Jawa2.Lesson8.server.Server;
import ru.geekbrains.Jawa2.Lesson8.server.api.AuthService;
import ru.geekbrains.Jawa2.Lesson8.server.model.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AuthenticationTask extends Thread {
    private Socket socket;
    private Server server;
    private AuthService authService;

    public AuthenticationTask(Socket socket, Server server, AuthService authService) {
        this.socket = socket;
        this.server = server;
        this.authService = authService;
    }

    @Override
    public void run() {
        String line;
        DataInputStream in;
        DataOutputStream out;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            while (true){
                line = in.readUTF();
                if (line.startsWith("/auth")){
                    String[] creds = line.split(" ");
                    Client client = authService.authenticate(creds[1], creds[2]);
                    if (client != null){
                        out.writeUTF("/auth_success");
                        out.writeUTF("/setNick " + client.getNick());
                        server.subscribe(new ClientHandler(server, socket, client));
                        break;
                    }else {
                        out.writeUTF("/auth_failed");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
