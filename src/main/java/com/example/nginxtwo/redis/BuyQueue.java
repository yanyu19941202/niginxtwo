package com.example.nginxtwo.redis;

import java.util.concurrent.LinkedBlockingQueue;

public class BuyQueue<BuyRequest> extends LinkedBlockingQueue<BuyRequest> {

    public BuyQueue(int capacity) {
        super(capacity);

    }
}

