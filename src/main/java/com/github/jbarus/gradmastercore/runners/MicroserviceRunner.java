package com.github.jbarus.gradmastercore.runners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jbarus.gradmastercore.consumers.ProblemConsumer;
import com.github.jbarus.gradmastercore.mappers.SolutionMapper;
import com.github.jbarus.gradmastercore.models.Problem;
import com.github.jbarus.gradmastercore.models.dto.SolutionDTO;
import com.github.jbarus.gradmastercore.solver.CommitteeSolution;
import com.github.jbarus.gradmastercore.solver.SolverRunner;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.github.cdimascio.dotenv.Dotenv;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MicroserviceRunner {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition problemAvailable = lock.newCondition();
    private static final Dotenv dotenv = Dotenv.load();

    public void run() {
        ConnectionFactory factory = createConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            ProblemConsumer consumer = new ProblemConsumer(channel, lock, problemAvailable);
            consumer.startConsuming();

            System.out.println("Waiting for messages in RabbitMQ queue...");

            while (true) {
                lock.lock();
                try {
                    while (Problem.getCurrentInstance() == null) {
                        problemAvailable.await();
                    }

                    System.out.println("Starting calculations for the problem...");

                    SolverRunner solverRunner = new SolverRunner();
                    CommitteeSolution solved = solverRunner.solve();
                    SolutionDTO solutionDTO = SolutionMapper.convertCommitteeSolutionToSolutionDTO(solved);
                    publishSolution(channel, solutionDTO);
                    Problem.setCurrentInstance(null);
                    System.out.println("Calculations complete. Ready for next problem.");

                } finally {
                    lock.unlock();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ConnectionFactory createConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(dotenv.get("RABBITMQ_HOST"));
        factory.setPort(Integer.parseInt(dotenv.get("RABBITMQ_PORT")));
        factory.setUsername(dotenv.get("RABBITMQ_USERNAME"));
        factory.setPassword(dotenv.get("RABBITMQ_PASSWORD"));
        return factory;
    }

    private void publishSolution(Channel channel, SolutionDTO solution) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String solutionJson = objectMapper.writeValueAsString(solution);

            channel.basicPublish("gradmaster", "gradmaster.solution", null, solutionJson.getBytes(StandardCharsets.UTF_8));
            System.out.println("Solution published to 'solution' queue: " + solutionJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
