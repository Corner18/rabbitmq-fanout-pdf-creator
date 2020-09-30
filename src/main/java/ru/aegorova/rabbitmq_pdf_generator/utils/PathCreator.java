package ru.aegorova.rabbitmq_pdf_generator.utils;


import ru.aegorova.rabbitmq_pdf_generator.model.User;


public class PathCreator {

    public String createDismissalPath(String path, User user) {
        return path +
                "dismissal_" +
                user.getPassport()
                + ".pdf";
    }

    public String createVacationPath(String path, User user) {
        return path +
                "vacation_" +
                user.getPassport()
                + ".pdf";
    }
}
