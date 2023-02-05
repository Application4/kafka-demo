package com.javatechie.controller;

import com.javatechie.dto.PaymentRequest;
import com.javatechie.dto.PaytmRequest;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "NewTopic";

    @GetMapping("/publish/{message}")
    public String sendMessage(@PathVariable String message) throws InterruptedException {
        //for(int i = 0; i< 100000; i++){
            kafkaTemplate.send(TOPIC, message);
            System.out.println(System.currentTimeMillis());
        //}

        return message;
    }

    @PostMapping("/pay")
    public String processPayment(@RequestBody PaytmRequest<PaymentRequest> paytmRequest) {
        kafkaTemplate.send("PAYMENT_TOPIC", paytmRequest);
        return "Payment initiated successfully...";
    }
}
