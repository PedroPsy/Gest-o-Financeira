package com.example.finances.web;

import com.example.finances.service.ReportService;
import com.example.finances.service.UserService;
import com.example.finances.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reports;
    private final UserService users;
    public ReportController(ReportService reports, UserService users){
        this.reports = reports; this.users = users;
    }

    @GetMapping("/monthly-balance")
    public Map<String, Object> monthlyBalance(@AuthenticationPrincipal UserDetails ud,
                                              @RequestParam String ym){
        User u = users.findByEmail(ud.getUsername());
        YearMonth yearMonth = YearMonth.parse(ym);
        BigDecimal balance = reports.monthlyBalance(u, yearMonth);
        Map<String, Object> m = new HashMap<>();
        m.put("yearMonth", ym);
        m.put("balance", balance);
        return m;
    }

    @GetMapping("/category-summary")
    public Map<String, Object> categorySummary(@AuthenticationPrincipal UserDetails ud,
                                               @RequestParam String ym){
        User u = users.findByEmail(ud.getUsername());
        YearMonth yearMonth = YearMonth.parse(ym);
        Map<String, Object> data = reports.monthTotalsByCategory(u, yearMonth);
        Map<String, Object> resp = new HashMap<>();
        resp.put("labels", data.keySet());
        resp.put("values", data.values());
        return resp;
    }
}
