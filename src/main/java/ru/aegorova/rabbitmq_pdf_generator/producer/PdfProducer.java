package ru.aegorova.rabbitmq_pdf_generator.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ru.aegorova.rabbitmq_pdf_generator.model.User;
import ru.aegorova.rabbitmq_pdf_generator.utils.DataReader;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class PdfProducer {

    private final static String EXCHANGE_NAME = "pdfs";
    private final static String EXCHANGE_TYPE = "fanout";


    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        DataReader dataReader = new DataReader();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // создаем exchange
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            // считываем данные пользователя
            User user = dataReader.read();
            // генерируем джейсон
            String userJson = objectMapper.writeValueAsString(user);
            System.out.println(userJson);
            // отправляем пользователя в иксчейндж
            channel.basicPublish(EXCHANGE_NAME, "", null, userJson.getBytes());
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

}

