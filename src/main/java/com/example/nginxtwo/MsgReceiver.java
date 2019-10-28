package com.example.nginxtwo;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.nginxtwo.RabbitConfig.QUEUE_A;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiver {

    @RabbitHandler
    public void process(String content, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {

        //channel.basicConsume(QUEUE_A, false,new DefaultConsumer(channel));


        System.out.println("接收处理队列A当中的消息： " + content);
    }

}
