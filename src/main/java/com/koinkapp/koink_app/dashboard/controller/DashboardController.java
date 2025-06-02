package com.koinkapp.koink_app.dashboard.controller;

import com.koinkapp.koink_app.dashboard.dto.DashboardResponse;
import com.koinkapp.koink_app.dashboard.service.DashboardService;
import com.koinkapp.koink_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashBoard(
            Authentication authentication,
            @RequestParam(defaultValue = "current_month") String period
    ) {
        User user = (User) authentication.getPrincipal();
        LocalDate startDate;
        LocalDate endDate;
        LocalDate today = LocalDate.now();

        switch (period) {
            case "last_month":
                YearMonth lastMonth = YearMonth.from(today.minusMonths(1));
                startDate = lastMonth.atDay(1);
                endDate = lastMonth.atEndOfMonth();
                break;
            case "current_year":
                startDate = today.withDayOfYear(1);
                endDate = today;
                break;
            case "current_month":
            default:
                YearMonth currentMonth = YearMonth.from(today);
                startDate = currentMonth.atDay(1);
                endDate = today;
                break;
        }

        DashboardResponse response = dashboardService.getDashboard(user, startDate, endDate);
        return ResponseEntity.ok(response);
    }
}