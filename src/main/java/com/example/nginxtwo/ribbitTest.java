package com.example.nginxtwo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ribbitTest {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/ribbitsend")
    public  String sendRibbitMsg(String msg){

        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A\
        rabbitTemplate.setMandatory(true);
        //rabbitTemplate.convertSendAndReceive(RabbitConfig.EXCHANGE_B,"", msg);
        rabbitTemplate.convertSendAndReceive(RabbitConfig.EXCHANGE_A, RabbitConfig.ROUTINGKEY_A, msg, correlationId);


          return msg;



    }

}
