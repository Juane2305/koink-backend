package com.koinkapp.koink_app.report.service;

import com.koinkapp.koink_app.report.dto.MonthlyCategoryReportDTO;
import com.koinkapp.koink_app.report.dto.MonthlySpendingDTO;
import com.koinkapp.koink_app.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransactionRepository transactionRepository;

    public List<MonthlyCategoryReportDTO> getSpendingByCategory(Long userId, int month, int year) {
        return transactionRepository.getMonthlySpendingByCategory(userId, month, year);
    }

    public List<MonthlySpendingDTO> getMonthlySpending(Long userId, int year) {
        return transactionRepository.getMonthlySpendingByYear(userId, year);
    }
}
