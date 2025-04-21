package com.koinkapp.koink_app.dashboard.controller;

import com.koinkapp.koink_app.dashboard.dto.DashboardResponse;
import com.koinkapp.koink_app.dashboard.service.DashboardService;
import com.koinkapp.koink_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashBoard(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        DashboardResponse response = dashboardService.getDashboard(user);
        return ResponseEntity.ok(response);
    }
}
