package com.koinkapp.koink_app.report.controller;

import com.koinkapp.koink_app.export.service.PdfReportService;
import com.koinkapp.koink_app.report.dto.MonthlyCategoryReportDTO;
import com.koinkapp.koink_app.report.dto.MonthlySpendingDTO;
import com.koinkapp.koink_app.report.service.ReportService;
import com.koinkapp.koink_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final PdfReportService pdfReportService;

    @GetMapping("/monthly")
    public List<MonthlyCategoryReportDTO> getMonthlyReport(
            @RequestParam int month,
            @RequestParam int year,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return reportService.getSpendingByCategory(user.getId(), month, year);
    }

    @GetMapping("/yearly")
    public List<MonthlySpendingDTO> getYearlySpending(
            @RequestParam int year,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return reportService.getMonthlySpending(user.getId(), year);
    }


    @GetMapping(value = "/yearly/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getYearlyReportPdf(
            @RequestParam int year,
            @AuthenticationPrincipal User user
    ) {
        byte[] pdfBytes = pdfReportService.generateYearlyReportPDF(user, year, reportService.getMonthlySpending(user.getId(), year));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("reporte-" + year + ".pdf")
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
