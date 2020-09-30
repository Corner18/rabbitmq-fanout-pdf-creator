package ru.aegorova.rabbitmq_pdf_generator.utils;


import ru.aegorova.rabbitmq_pdf_generator.model.User;


public class TextCreator {

    public String createDismissalText(User user){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fired");
        return createText(stringBuilder, user);
    }

    public String createVacationText(User user){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("vacation");
        return createText(stringBuilder, user);
    }

    public String createText(StringBuilder stringBuilder, User user){
        stringBuilder
                .append("name: - ")
                .append(user.getName())
                .append(", surname - ")
                .append(user.getSurname())
                .append(", age - ")
                .append(user.getAge())
                .append(", passport - ")
                .append(user.getPassport())
                .append(", ")
                .append(user.getPassport_date());
        return stringBuilder.toString();
    }
}

