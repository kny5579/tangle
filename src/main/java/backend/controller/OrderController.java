package backend.controller;

import backend.entity.Order;
import backend.service.OrderService;
import backend.util.ExcelExporter;
import backend.util.ExcelImporter;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

        setupRowContextMenu();
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("엑셀 파일 저장");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel 파일 (*.xlsx)", "*.xlsx")
        );

        File file = fileChooser.showSaveDialog(nameField.getScene().getWindow());

        if (file != null) {
            if (!file.getName().toLowerCase().endsWith(".xlsx")) {
                file = new File(file.getAbsolutePath() + ".xlsx");
            }

            try {
                ExcelExporter.exportToExcel(orderList, file);
                System.out.println("엑셀 저장 완료");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("엑셀 저장 실패");
            }
        }

    }

    @FXML
    public void onLoadExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("엑셀 파일 열기");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel 파일 (*.xlsx)", "*.xlsx")
        );

        File file = fileChooser.showOpenDialog(nameField.getScene().getWindow());
        if (file != null) {
            try {
                List<Order> loadedOrders = ExcelImporter.importFromExcel(file);
                orderList.setAll(loadedOrders);
                System.out.println("엑셀 불러오기 완료");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("엑셀 불러오기 실패");
            }
        }
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


    private void setupRowContextMenu() {
        orderTable.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();

            ContextMenu rowMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem("삭제");
            deleteItem.setOnAction(e -> {
                Order selected = row.getItem();
                if (selected != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "정말 삭제하시겠습니까?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            service.deleteOrder(selected);
                            orderList.remove(selected);
                        }
                    });
                }
            });
            rowMenu.getItems().add(deleteItem);

            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(rowMenu)
            );

            return row;
        });
    }

    private void clearFields() {
        nameField.clear(); phoneField.clear(); addressField.clear();
        quantityField.clear(); itemNameField.clear();
        senderNameField.clear(); senderPhoneField.clear();
    }

    private void setupAutoCompletion() {
        TextFields.bindAutoCompletion(nameField, request ->
                service.suggestReceiverOrders(request.getUserText())
        ).setOnAutoCompleted(event -> {
            Order selected = event.getCompletion();
            fillFormFields(selected);
        });

        TextFields.bindAutoCompletion(phoneField,
                request -> service.suggestReceiverByPhone(request.getUserText()),
                new StringConverter<Order>() {
                    @Override
                    public String toString(Order order) {
                        return order.getPhone();
                    }

                    @Override
                    public Order fromString(String string) {
                        return null;
                    }
                }).setOnAutoCompleted(event -> {
            fillFormFields(event.getCompletion());
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
