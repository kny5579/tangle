package backend.controller;

import backend.entity.Order;
import backend.util.ExcelExporter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// ui 이벤트 처리
public class OrderController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField quantityField;
    @FXML private TextField itemNameField;
    @FXML private TextField senderNameField;
    @FXML private TextField senderPhoneField;

    private final List<Order> orderList = new ArrayList<>();

    @FXML
    public void onSave() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String itemName = itemNameField.getText();
        String sender = senderNameField.getText();
        String senderPhone = senderPhoneField.getText();
        int quantity;

        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            System.out.println("수량은 숫자로 입력하세요.");
            return;
        }

        Order order = Order.of(name, address, phone, quantity, itemName, sender, senderPhone);
        orderList.add(order);

        try {
            ExcelExporter.exportToExcel(orderList, "주문내역.xlsx");
            System.out.println("엑셀 저장 성공");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 입력창 초기화
        nameField.clear();
        phoneField.clear();
        addressField.clear();
        quantityField.clear();
        itemNameField.clear();
        senderNameField.clear();
        senderPhoneField.clear();
    }
}
