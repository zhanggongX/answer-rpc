package tech.zg.answer.example.server.service;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import tech.zg.answer.common.annotation.AnswerService;
import tech.zg.answer.example.server.api.OrderService;
import tech.zg.answer.example.server.bean.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AnswerService(OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Override
    public void createOrder() {
        log.info("createOrder success");
    }

    @Override
    public List<Order> listOrder() {

        Order order1 = new Order();
        order1.setId(1L);
        order1.setName("订单1");

        Order order2 = new Order();
        order2.setId(2L);
        order2.setName("订单2");

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        log.info("返回订单信息-{}", JSON.toJSONString(orders));
        return orders;
    }
}
