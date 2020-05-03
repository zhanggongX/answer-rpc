package tech.zg.answer.example.app.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.zg.answer.client.AnswerClientProxy;
import tech.zg.answer.example.server.api.OrderService;
import tech.zg.answer.example.server.bean.Order;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private AnswerClientProxy answerClientProxy;

    @GetMapping("/create")
    public String create() {
        OrderService orderService = answerClientProxy.create(OrderService.class);
        orderService.createOrder();
        return "创建订单成功";
    }

    @GetMapping("/listOrder")
    public String listOrder() {
        OrderService orderService = answerClientProxy.create(OrderService.class);
        List<Order> orders = orderService.listOrder();
        return JSON.toJSONString(orders);
    }
}
