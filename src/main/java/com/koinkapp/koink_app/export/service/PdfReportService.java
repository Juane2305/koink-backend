package com.koinkapp.koink_app.export.service;

import com.koinkapp.koink_app.report.dto.MonthlySpendingDTO;
import com.koinkapp.koink_app.user.model.User;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class PdfReportService {

    public byte[] generateYearlyReportPDF(User user, int year, List<MonthlySpendingDTO> data) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font dataFont = new Font(Font.HELVETICA, 12);

            document.add(new Paragraph("Reporte anual de gastos", titleFont));
            document.add(new Paragraph("Usuario: " + user.getName() + " (" + user.getEmail() + ")", dataFont));
            document.add(new Paragraph("Año: " + year, dataFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.addCell("Mes");
            table.addCell("Total Gastado");

            for (MonthlySpendingDTO item : data) {
                String monthName = Month.of(item.getMonth()).getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                table.addCell(capitalize(monthName));
                table.addCell("$" + item.getTotalSpent());
            }

            BigDecimal totalAnual = data.stream()
                    .map(MonthlySpendingDTO::getTotalSpent)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Agregamos la tabla primero
            document.add(table);
            document.add(new Paragraph(" ")); // espacio visual

            // Formateo con puntos y comas al estilo argentino
            NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("es", "AR"));
            numberFormat.setMinimumFractionDigits(2);
            numberFormat.setMaximumFractionDigits(2);
            String formattedTotal = numberFormat.format(totalAnual);

            Paragraph totalParagraph = new Paragraph("Total gastado en el año: $" + formattedTotal, titleFont);
            document.add(totalParagraph);

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando el PDF", e);
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
