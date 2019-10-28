package com.example.nginxtwo;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_B)
public class MsgReceiverC_two {



        @RabbitHandler
        public void process(String content) {

            System.out.println("处理器two接收处理队列A当中的消息： " + content);
        }



}
