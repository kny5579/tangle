package backend.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Order {
    private final StringProperty receiverName = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final StringProperty itemName = new SimpleStringProperty();
    private final StringProperty senderName = new SimpleStringProperty();
    private final StringProperty senderPhone = new SimpleStringProperty();

    public Order(String receiverName, String address, String phone, int quantity, String itemName, String senderName, String senderPhone) {
        this.receiverName.set(receiverName);
        this.address.set(address);
        this.phone.set(phone);
        this.quantity.set(quantity);
        this.itemName.set(itemName);
        this.senderName.set(senderName);
        this.senderPhone.set(senderPhone);
    }

    public StringProperty receiverNameProperty() { return receiverName; }
    public StringProperty addressProperty() { return address; }
    public StringProperty phoneProperty() { return phone; }
    public IntegerProperty quantityProperty() { return quantity; }
    public StringProperty itemNameProperty() { return itemName; }
    public StringProperty senderNameProperty() { return senderName; }
    public StringProperty senderPhoneProperty() { return senderPhone; }

    public String getReceiverName() { return receiverName.get(); }
    public String getAddress() { return address.get(); }
    public String getPhone() { return phone.get(); }
    public int getQuantity() { return quantity.get(); }
    public String getItemName() { return itemName.get(); }
    public String getSenderName() { return senderName.get(); }
    public String getSenderPhone() { return senderPhone.get(); }
}
