package  ru.geekbrains.Jawa2.Lesson8.fxClient;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.DataOutputStream;
import java.io.IOException;

public class PrivateChatController {
    public TextArea text;
    public Button sendButton;

    public void btnClick() {
        DataOutputStream out = ((ChatStage) sendButton.getScene().getWindow()).getOut();
        String nickTo = ((ChatStage) sendButton.getScene().getWindow()).getNickTo();

        try {
            out.writeUTF("/w " + nickTo + " " + text.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
