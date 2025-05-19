package backend.repository;

import backend.entity.Order;
import javafx.collections.ObservableList;

import java.sql.*;

public class OrderRepository {
    private static final String DB_URL = "jdbc:sqlite:orders.db";

    public void initTable() {
        String sql = "CREATE TABLE IF NOT EXISTS orders (" +
                "receiver_name TEXT, address TEXT, phone TEXT, quantity INTEGER, " +
                "item_name TEXT, sender_name TEXT, sender_phone TEXT)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertOrder(Order order) {
        String sql = "INSERT INTO orders (receiver_name, address, phone, quantity, item_name, sender_name, sender_phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getReceiverName());
            pstmt.setString(2, order.getAddress());
            pstmt.setString(3, order.getPhone());
            pstmt.setInt(4, order.getQuantity());
            pstmt.setString(5, order.getItemName());
            pstmt.setString(6, order.getSenderName());
            pstmt.setString(7, order.getSenderPhone());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAllOrders(ObservableList<Order> list) {
        String sql = "SELECT * FROM orders";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getString("receiver_name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("quantity"),
                        rs.getString("item_name"),
                        rs.getString("sender_name"),
                        rs.getString("sender_phone")
                );
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(Order order) {
        String sql = """
            UPDATE orders SET
                address = ?, phone = ?, quantity = ?, item_name = ?, sender_name = ?, sender_phone = ?
            WHERE receiver_name = ?
            """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getAddress());
            pstmt.setString(2, order.getPhone());
            pstmt.setInt(3, order.getQuantity());
            pstmt.setString(4, order.getItemName());
            pstmt.setString(5, order.getSenderName());
            pstmt.setString(6, order.getSenderPhone());
            pstmt.setString(7, order.getReceiverName()); // WHERE 조건
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
