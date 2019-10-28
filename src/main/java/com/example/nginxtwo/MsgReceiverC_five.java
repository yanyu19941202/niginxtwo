package com.example.nginxtwo;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_C)
public class MsgReceiverC_five {



    @RabbitHandler
    public void process(String content) {

        System.out.println("five接收处理队列C当中的消息： " + content);
    }



}
