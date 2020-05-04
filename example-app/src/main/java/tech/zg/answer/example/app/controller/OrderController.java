package tech.zg.answer.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.zg.answer.client.AnswerClientProxy;
import tech.zg.answer.example.server.api.OrderService;
import tech.zg.answer.example.server.bean.Order;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private AnswerClientProxy answerClientProxy;

    @GetMapping("/create")
    public Order create() {
        OrderService orderService = answerClientProxy.create(OrderService.class);
        return orderService.createOrder(1);
    }

    @GetMapping("/listOrder")
    public Order listOrder() {
        OrderService orderService = answerClientProxy.create(OrderService.class);
        return orderService.listOrder(2);
    }
}
