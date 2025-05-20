package backend.controller;

import backend.entity.Order;
import backend.service.OrderService;
import backend.util.ExcelExporter;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;

public class OrderController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField quantityField;
    @FXML private TextField itemNameField;
    @FXML private TextField senderNameField;
    @FXML private TextField senderPhoneField;

    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Number> idCol;
    @FXML private TableColumn<Order, String> receiverCol;
    @FXML private TableColumn<Order, String> addressCol;
    @FXML private TableColumn<Order, String> phoneCol;
    @FXML private TableColumn<Order, Integer> quantityCol;
    @FXML private TableColumn<Order, String> itemNameCol;
    @FXML private TableColumn<Order, String> senderCol;
    @FXML private TableColumn<Order, String> senderPhoneCol;

    private final ObservableList<Order> orderList = FXCollections.observableArrayList();
    private final OrderService service = new OrderService();

    @FXML
    public void initialize() {
        setupTableView();
        service.initializeTable();
        service.loadOrders(orderList);
        orderTable.setItems(orderList);

        setupAutoCompletion();
    }

    private void setupTableView() {
        orderTable.setEditable(true);

        idCol.setCellValueFactory(cell -> cell.getValue().idProperty());
        receiverCol.setCellValueFactory(cell -> cell.getValue().receiverNameProperty());
        addressCol.setCellValueFactory(cell -> cell.getValue().addressProperty());
        phoneCol.setCellValueFactory(cell -> cell.getValue().phoneProperty());
        quantityCol.setCellValueFactory(cell -> cell.getValue().quantityProperty().asObject());
        itemNameCol.setCellValueFactory(cell -> cell.getValue().itemNameProperty());
        senderCol.setCellValueFactory(cell -> cell.getValue().senderNameProperty());
        senderPhoneCol.setCellValueFactory(cell -> cell.getValue().senderPhoneProperty());

        receiverCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        itemNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        senderCol.setCellFactory(TextFieldTableCell.forTableColumn());
        senderPhoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        receiverCol.setOnEditCommit(e -> {
            Order order = e.getRowValue();
            order.setReceiverName(e.getNewValue());
            service.update(order);
        });
        addressCol.setOnEditCommit(e -> {
            Order order = e.getRowValue();
            order.setAddress(e.getNewValue());
            service.update(order);
        });
        phoneCol.setOnEditCommit(e -> {
            Order order = e.getRowValue();
            order.setPhone(e.getNewValue());
            service.update(order);
        });
        quantityCol.setOnEditCommit(e -> {
            Order order = e.getRowValue();
            order.setQuantity(e.getNewValue());
            service.update(order);
        });
        itemNameCol.setOnEditCommit(e -> {
            Order order = e.getRowValue();
            order.setItemName(e.getNewValue());
            service.update(order);
        });
        senderCol.setOnEditCommit(e -> {
            Order order = e.getRowValue();
            order.setSenderName(e.getNewValue());
            service.update(order);
        });
        senderPhoneCol.setOnEditCommit(e -> {
            Order order = e.getRowValue();
            order.setSenderPhone(e.getNewValue());
            service.update(order);
        });
    }

    @FXML
    public void onAdd() {
        try {
            Order order = new Order(
                    nameField.getText(),
                    addressField.getText(),
                    phoneField.getText(),
                    Integer.parseInt(quantityField.getText()),
                    itemNameField.getText(),
                    senderNameField.getText(),
                    senderPhoneField.getText()
            );
            service.save(order);
            orderList.add(order);
            clearFields();
        } catch (NumberFormatException e) {
            System.out.println("수량은 숫자로 입력하세요.");
        }
    }

    @FXML
    public void onSave() {
        try {
            ExcelExporter.exportToExcel(orderList, "주문내역.xlsx");
            System.out.println("엑셀 저장 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.clear(); phoneField.clear(); addressField.clear();
        quantityField.clear(); itemNameField.clear();
        senderNameField.clear(); senderPhoneField.clear();
    }

    private void setupAutoCompletion() {
        TextFields.bindAutoCompletion(nameField, request ->
                service.suggestReceiverNames(request.getUserText())
        ).setOnAutoCompleted(event -> {
            Order selected = service.findByReceiverName(event.getCompletion());
            if (selected != null) {
                fillFormFields(selected);
            }
        });
    }

    private void fillFormFields(Order order) {
        nameField.setText(order.getReceiverName());
        phoneField.setText(order.getPhone());
        addressField.setText(order.getAddress());
        quantityField.setText(String.valueOf(order.getQuantity()));
        itemNameField.setText(order.getItemName());
        senderNameField.setText(order.getSenderName());
        senderPhoneField.setText(order.getSenderPhone());
    }


}
