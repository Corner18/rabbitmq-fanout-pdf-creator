package ru.aegorova.rabbitmq_pdf_generator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String name;
    private String surname;
    private String passport;
    private String age;
    private String passport_date;
}
