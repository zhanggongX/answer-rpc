package tech.zg.answer.example.server.service;


import lombok.extern.slf4j.Slf4j;
import tech.zg.answer.common.annotation.AnswerService;
import tech.zg.answer.example.server.api.OrderService;
import tech.zg.answer.example.server.bean.Order;

@Slf4j
@AnswerService(OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Override
    public Order createOrder(Integer param) {
        log.info("参数-{}", param);
        Order order2 = new Order();
        order2.setId(2L);
        order2.setName("订单2");
        return order2;
    }

    @Override
    public Order listOrder(Integer param) {
        log.info("参数-{}", param);
        Order order1 = new Order();
        order1.setId(1L);
        order1.setName("订单1");
        return order1;
    }
}
