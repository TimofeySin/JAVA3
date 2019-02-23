package  ru.geekbrains.Jawa2.Lesson8.fxClient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {

    //chat pane elements
    public AnchorPane chatPane;
    public TextField messageField;
    public Button sendButton;
    public TextArea textArea;
    public ListView<String> userList;

    //login pane elements
    public GridPane loginPane;
    public TextField loginField;
    public PasswordField passField;
    public Button loginBtn;

    private final String HOST = "localhost";
    private final int PORT = 8080;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        out.writeUTF(messageField.getText() + "\n");
        messageField.clear();
        messageField.requestFocus();
    }

    public void tryAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            initSocket();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            loginField.clear();
            passField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initSocket() {
        try {
            socket = new Socket(HOST, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Thread tr = new Thread(() -> {
                try {
                    String message;
                    while (true) {
                        message = in.readUTF();
                        if (message.startsWith("/auth_success")) {
                            authenticate(true);
                            break;
                        }
                        if (message.startsWith("/auth_failed")) {
                            Platform.runLater(this::alert);
                            System.out.println("auth failed: " + message);
                        }
                    }

                    while (true) {
                        message = in.readUTF();
                        if (message.startsWith("/setNick ")) {
                            Stage stage = (Stage) chatPane.getScene().getWindow();
                            String finalMessage = message;
                            Platform.runLater(() -> stage.setTitle("Chat-" + finalMessage.split(" ")[1]));
                        }
                        if (message.equals("/Close")) {
                            Platform.exit();
                            System.out.println("Close time out");
                        }
                        if (message.startsWith("/")) {
                            if (message.startsWith("/users ")) {
                                String[] users = message.split(" ");

                                Platform.runLater(() -> {
                                    userList.getItems().clear();
                                    for (int i = 1; i < users.length; i++) {
                                        userList.getItems().add(users[i]);
                                    }
                                });
                            }

                        } else {
                            System.out.print("from server:" + message);
                            textArea.appendText(message);
                        }
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
                }
            });
            tr.setDaemon(true);
            tr.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authenticate(boolean auth) {
        loginPane.setVisible(!auth);
        loginPane.setManaged(!auth);

        chatPane.setVisible(auth);
        chatPane.setManaged(auth);
    }

    public void selectClient(MouseEvent event) {
        if (event.getClickCount() == 2) {
            ChatStage chatStage = new ChatStage(userList.getSelectionModel().getSelectedItem(), out);
            chatStage.show();
        }
    }

    private void alert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Auth error");
        alert.setContentText("Неправильный логин или пароль");
        alert.showAndWait();
    }
}
