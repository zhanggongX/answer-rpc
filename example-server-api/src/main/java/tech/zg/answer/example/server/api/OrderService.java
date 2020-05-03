package tech.zg.answer.example.server.api;

import tech.zg.answer.example.server.bean.Order;

import java.util.List;

public interface OrderService {

    void createOrder();

    List<Order> listOrder();
}
