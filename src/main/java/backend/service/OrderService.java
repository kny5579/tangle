package backend.service;

import backend.entity.Order;
import backend.repository.OrderRepository;
import javafx.collections.ObservableList;

public class OrderService {

    private final OrderRepository repository;

    public OrderService() {
        this.repository = new OrderRepository();
    }

    public void save(Order order) {
        repository.insertOrder(order);
    }

    public void loadOrders(ObservableList<Order> list) {
        repository.loadAllOrders(list);
    }

    public void initializeTable() {
        repository.initTable();
    }

    public void update(Order order) {
        repository.updateOrder(order);
    }
}
