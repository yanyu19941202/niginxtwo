package com.example.nginxtwo;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

//RabbitTemplate.ConfirmCallback
@Service
public class HelloSender implements RabbitTemplate.ReturnCallback {




/*
/消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

//ack返回false，并重新回到队列，api里面解释得很清楚

channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);

//拒绝消息

channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);*/

    @Autowired
    //private AmqpTemplate rabbitTemplate;
    private RabbitTemplate rabbitTemplate;
    public void send(String msg) {
        //String context = "你好现在是 " + new Date() +"";
        System.out.println("HelloSender发送内容 : " + msg);
        // this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {

                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ack);
                System.out.println("ConfirmCallback:     "+"原因："+cause);
            if (!ack) {
                System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
            } else {
                System.out.println("HelloSender 消息发送成功 ");
            }
        });
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        this.rabbitTemplate.setMandatory(true);

        this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A, RabbitConfig.ROUTINGKEY_A, msg, correlationId);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("sender return success" + message.toString()+"==="+i+"==="+s1+"==="+s2);
    }

     /*  @Override
       public void confirm(CorrelationData correlationData, boolean b, String s) {
            System.out.println("sender success");
        }*/

}