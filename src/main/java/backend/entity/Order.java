package backend.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty receiverName = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final StringProperty itemName = new SimpleStringProperty();
    private final StringProperty senderName = new SimpleStringProperty();
    private final StringProperty senderPhone = new SimpleStringProperty();

    public Order(int id, String receiverName, String address, String phone, int quantity, String itemName, String senderName, String senderPhone) {
        this.id.set(id);
        this.receiverName.set(receiverName);
        this.address.set(address);
        this.phone.set(phone);
        this.quantity.set(quantity);
        this.itemName.set(itemName);
        this.senderName.set(senderName);
        this.senderPhone.set(senderPhone);
    }

    public Order(String receiverName, String address, String phone, int quantity, String itemName, String senderName, String senderPhone) {
        this(0, receiverName, address, phone, quantity, itemName, senderName, senderPhone);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty receiverNameProperty() { return receiverName; }
    public StringProperty addressProperty() { return address; }
    public StringProperty phoneProperty() { return phone; }
    public IntegerProperty quantityProperty() { return quantity; }
    public StringProperty itemNameProperty() { return itemName; }
    public StringProperty senderNameProperty() { return senderName; }
    public StringProperty senderPhoneProperty() { return senderPhone; }

    public int getId() { return id.get(); }
    public String getReceiverName() { return receiverName.get(); }
    public String getAddress() { return address.get(); }
    public String getPhone() { return phone.get(); }
    public int getQuantity() { return quantity.get(); }
    public String getItemName() { return itemName.get(); }
    public String getSenderName() { return senderName.get(); }
    public String getSenderPhone() { return senderPhone.get(); }

    public void setId(int id) { this.id.set(id); }
    public void setReceiverName(String value) { receiverName.set(value); }
    public void setAddress(String value) { address.set(value); }
    public void setPhone(String value) { phone.set(value); }
    public void setQuantity(int value) { quantity.set(value); }
    public void setItemName(String value) { itemName.set(value); }
    public void setSenderName(String value) { senderName.set(value); }
    public void setSenderPhone(String value) { senderPhone.set(value); }

    @Override
    public String toString() {
        return getReceiverName();
    }
}
