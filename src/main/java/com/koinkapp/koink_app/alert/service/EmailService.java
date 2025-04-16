package com.koinkapp.koink_app.alert.service;

import com.koinkapp.koink_app.budget.model.Budget;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;


    public void sendBudgetExceededAlert(Budget budget, BigDecimal totalSpent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(budget.getUser().getEmail());
        message.setSubject("⚠ ¡Presupuesto superado!");

        String body = String.format(
                "Hola %s,\n\n" +
                        "Se ha superado el presupuesto de la categoría \"%s\" para el período %s a %s.\n\n" +
                        "🔸 Monto asignado: $%s\n" +
                        "🔻 Total gastado: $%s\n\n" +
                        "Te recomendamos revisar tus finanzas en Koink para ajustar tus gastos.\n\n" +
                        "📊 Koink - Finanzas personales",
                budget.getUser().getName(),
                budget.getCategory().getName(),
                budget.getStartDate().format(formatter),
                budget.getEndDate().format(formatter),
                budget.getLimitAmount(),
                totalSpent
        );

        message.setText(body);
        mailSender.send(message);
    }

}
