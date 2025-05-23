package backend.repository;

import backend.entity.Order;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private static final String DB_URL = "jdbc:sqlite:orders.db";

    public void initTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS orders (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                receiver_name TEXT,
                address TEXT,
                phone TEXT,
                quantity INTEGER,
                item_name TEXT,
                sender_name TEXT,
                sender_phone TEXT
            )
            """;
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

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    order.setId(rs.getInt(1));
                }
            }

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
                        rs.getInt("id"),
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
                receiver_name = ?,
                address = ?,
                phone = ?,
                quantity = ?,
                item_name = ?,
                sender_name = ?,
                sender_phone = ?
            WHERE id = ?
            """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getReceiverName());
            pstmt.setString(2, order.getAddress());
            pstmt.setString(3, order.getPhone());
            pstmt.setInt(4, order.getQuantity());
            pstmt.setString(5, order.getItemName());
            pstmt.setString(6, order.getSenderName());
            pstmt.setString(7, order.getSenderPhone());
            pstmt.setInt(8, order.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderById(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Order> findOrdersByReceiverNamePrefix(String prefix) {
        String sql = """
        SELECT * FROM orders
        WHERE receiver_name LIKE ?
        ORDER BY receiver_name
        LIMIT 10
        """;
        List<Order> results = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, prefix + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                results.add(new Order(
                        rs.getInt("id"),
                        rs.getString("receiver_name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("quantity"),
                        rs.getString("item_name"),
                        rs.getString("sender_name"),
                        rs.getString("sender_phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Order> findOrdersByPhonePrefix(String prefix) {
        String sql = """
        SELECT * FROM orders
        WHERE phone LIKE ?
        ORDER BY phone
        LIMIT 10
        """;
        List<Order> results = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, prefix + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                results.add(new Order(
                        rs.getInt("id"),
                        rs.getString("receiver_name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("quantity"),
                        rs.getString("item_name"),
                        rs.getString("sender_name"),
                        rs.getString("sender_phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }


}