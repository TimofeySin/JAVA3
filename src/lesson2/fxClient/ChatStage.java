package  ru.geekbrains.Jawa2.Lesson8.fxClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;

public class ChatStage extends Stage {
    private String nickTo;
    private DataOutputStream out;

    public ChatStage(String nickTo, DataOutputStream out) {
        this.nickTo = nickTo;
        this.out = out;
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("private.fxml"));
            setTitle("personal chat");

            Scene scene = new Scene(root, 300, 500);
            setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickTo() {
        return nickTo;
    }

    public void setNickTo(String nickTo) {
        this.nickTo = nickTo;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }


}
