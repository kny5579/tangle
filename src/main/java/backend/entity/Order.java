package backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Order {
    private String receiverName;
    private String address;
    private String phone;
    private int quantity;
    private String itemName;
    private String senderName;
    private String senderPhone;

    public static Order of(String receiverName, String address, String phone, int quantity, String itemName, String senderName, String senderPhone) {
        return new Order(receiverName, address, phone, quantity, itemName, senderName, senderPhone);
    }
}
