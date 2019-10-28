package com.example.nginxtwo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.nginxtwo.RabbitConfig.QUEUE_A;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiverC_one {



    @RabbitHandler
    public void process(String content, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {




        //channel.basicAck(deliveryTag,false);
        System.out.println("处理器one接收处理队列A当中的消息： " + content);
    }
    /*@RabbitHandler
    public void process(String hello,Channel channel, Message message) throws IOException {
        System.out.println("HelloReceiver收到  : " + hello +"收到时间"+new Date());
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("receiver success");
        } catch (IOException e) {
            e.printStackTrace();
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            System.out.println("receiver fail");
        }

    }
*/
}
