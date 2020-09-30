package ru.aegorova.rabbitmq_pdf_generator.utils;


import ru.aegorova.rabbitmq_pdf_generator.model.User;

import java.util.Scanner;


public class DataReader {

    private static final Scanner SCANNER = new Scanner(System.in);

    public User read(){
        System.out.println("Ввведите имя: ");
        String name = SCANNER.nextLine();
        System.out.println("Ввведите фамилию: ");
        String surname = SCANNER.nextLine();
        System.out.println("Ввведите номер паспорта: ");
        String passport = SCANNER.nextLine();
        System.out.println("Ввведите возвраст: ");
        String age = SCANNER.nextLine();
        System.out.println("Ввведите дату выдачи: ");
        String passport_date = SCANNER.nextLine();
        return User.builder()
                .name(name)
                .surname(surname)
                .passport(passport)
                .age(age)
                .passport_date(passport_date)
                .build();
    }
}
