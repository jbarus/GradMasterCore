package com.github.jbarus.gradmastercore.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jbarus.gradmastercore.mappers.ProblemMapper;
import com.github.jbarus.gradmastercore.models.Problem;
import com.github.jbarus.gradmastercore.models.dto.ProblemDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ProblemConsumer {

    private final Channel channel;
    private final Lock lock;
    private final Condition problemAvailable;

    public ProblemConsumer(Channel channel, Lock lock, Condition problemAvailable) {
        this.channel = channel;
        this.lock = lock;
        this.problemAvailable = problemAvailable;
    }

    public void startConsuming() throws Exception {
        channel.queueDeclare("problem", true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                ProblemDTO problemData = parseMessage(message);

                lock.lock();
                try {
                    Problem.setCurrentInstance(ProblemMapper.problemDTOToProblemConverter(problemData));
                    problemAvailable.signal();
                } finally {
                    lock.unlock();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        channel.basicConsume("problem", true, deliverCallback, consumerTag -> {});
    }

    private ProblemDTO parseMessage(String message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(message, ProblemDTO.class);
    }
}