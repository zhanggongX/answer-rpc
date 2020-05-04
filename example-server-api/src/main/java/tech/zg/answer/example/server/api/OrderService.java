package tech.zg.answer.example.server.api;

import tech.zg.answer.example.server.bean.Order;

public interface OrderService {

    Order createOrder(Integer param);

    Order listOrder(Integer param);
}
