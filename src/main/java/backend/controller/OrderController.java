package backend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

// ui 이벤트 처리
public class OrderController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    public void onSave() {
        System.out.println("저장: " + nameField.getText());
    }
}
