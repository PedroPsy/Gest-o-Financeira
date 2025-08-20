package com.example.finances.service;

import com.example.finances.model.Expense;
import com.example.finances.model.Income;
import com.example.finances.model.Transaction;
import com.example.finances.model.User;
import com.example.finances.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {
    private final TransactionRepository repo;
    public ReportService(TransactionRepository repo){ this.repo = repo; }

    public BigDecimal monthlyBalance(User user, YearMonth ym){
        LocalDate from = ym.atDay(1);
        LocalDate to = ym.atEndOfMonth();
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;
        for (Transaction t : repo.findAll()) {
            if (!t.getUser().equals(user)) continue;
            if (t.getDate().isBefore(from) || t.getDate().isAfter(to)) continue;
            if (t instanceof Income) income = income.add(t.getValue());
            if (t instanceof Expense) expense = expense.add(t.getValue());
        }
        return income.subtract(expense);
    }

    public Map<String, BigDecimal> monthTotalsByCategory(User user, YearMonth ym){
        LocalDate from = ym.atDay(1);
        LocalDate to = ym.atEndOfMonth();
        Map<String, BigDecimal> map = new HashMap<>();
        for (Transaction t : repo.findAll()) {
            if (!t.getUser().equals(user)) continue;
            if (t.getDate().isBefore(from) || t.getDate().isAfter(to)) continue;
            if (t instanceof Expense) {
                String key = t.getCategory() != null ? t.getCategory().getName() : "Sem categoria";
                map.put(key, map.getOrDefault(key, BigDecimal.ZERO).add(t.getValue()));
            }
        }
        return map;
    }
}
