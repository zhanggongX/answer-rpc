package tech.zg.answer.example.server.service;


import tech.zg.answer.common.annotation.AnswerService;
import tech.zg.answer.example.server.api.OrderService;

@AnswerService(OrderService.class)
public class OrderServiceImpl implements OrderService {
}
