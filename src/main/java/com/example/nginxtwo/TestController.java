package com.example.nginxtwo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);


    @Autowired
    private HelloSender helloSender;

    @RequestMapping("/hellotest")
    public void hello(String msg) throws Exception {

        LOG.info("log=========="+msg);
        helloSender.send(msg);
    }
}
