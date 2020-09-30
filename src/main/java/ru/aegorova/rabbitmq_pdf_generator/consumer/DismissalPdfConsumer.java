package ru.aegorova.rabbitmq_pdf_generator.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import ru.aegorova.rabbitmq_pdf_generator.model.User;
import ru.aegorova.rabbitmq_pdf_generator.utils.PDFCreator;
import ru.aegorova.rabbitmq_pdf_generator.utils.PathCreator;
import ru.aegorova.rabbitmq_pdf_generator.utils.TextCreator;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@Slf4j
public class DismissalPdfConsumer {

    private static final String PATH = "/Users/anastasiaegorova/Documents/HomeWork/javalab3/rabbitmq-pdf/pdfs/";
    private final static String EXCHANGE_NAME = "pdfs";
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        ObjectMapper objectMapper = new ObjectMapper();
        TextCreator textCreator = new TextCreator();
        PathCreator pathCreator = new PathCreator();
        PDFCreator pdfCreator = new PDFCreator();

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);

            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            // создаем временную очередь со случайным названием
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, EXCHANGE_NAME, "");
            DeliverCallback deliverCallback = (consumerTag, message) -> {
                // получаем  json  usera
                String userJson = new String(message.getBody());
                // переделываем его в сущность
                User user = objectMapper.readValue(userJson, User.class);
                // генерим строку пдф
                String text = textCreator.createDismissalText(user);
                // генерим путь
                String path = pathCreator.createDismissalPath(PATH, user);
                try {
                    pdfCreator.createPdf(path, text);
                    log.info("PDF done " + path);
                    // подтверждение того, что сообщение было обработано
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (IllegalArgumentException e) {
                    log.error("FAILED");
                    // reject - отмена выполнения сообщения, false - нельзя назначать другим
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
