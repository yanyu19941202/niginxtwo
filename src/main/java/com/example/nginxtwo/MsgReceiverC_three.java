package com.example.nginxtwo;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_B)
public class MsgReceiverC_three {



    @RabbitHandler
    public void process(String content) {

        System.out.println("接收处理队列B当中的消息： " + content);
    }



}
