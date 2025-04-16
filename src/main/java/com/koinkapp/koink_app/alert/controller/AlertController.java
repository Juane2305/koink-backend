package com.koinkapp.koink_app.alert.controller;

import com.koinkapp.koink_app.alert.dto.BudgetExceededAlertDTO;
import com.koinkapp.koink_app.alert.service.BudgetAlertService;
import com.koinkapp.koink_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final BudgetAlertService alertService;

    @GetMapping("/budget-exceeded")
    public List<BudgetExceededAlertDTO> getExceededBudgets(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return alertService.getExceededBudgetsForUser(user.getId());
    }
}
