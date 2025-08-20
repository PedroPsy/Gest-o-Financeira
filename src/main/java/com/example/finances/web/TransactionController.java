package com.example.finances.web;

import com.example.finances.dto.TransactionRequest;
import com.example.finances.model.*;
import com.example.finances.repository.CategoryRepository;
import com.example.finances.service.TransactionService;
import com.example.finances.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService txs;
    private final CategoryRepository categories;
    private final UserService users;

    public TransactionController(TransactionService txs, CategoryRepository categories, UserService users){
        this.txs = txs; this.categories = categories; this.users = users;
    }

    @PostMapping
    public Transaction create(@Valid @RequestBody TransactionRequest req, @AuthenticationPrincipal UserDetails ud){
        User u = users.findByEmail(ud.getUsername());
        Transaction t = req.type.equalsIgnoreCase("INCOME") ? new Income() : new Expense();
        t.setDescription(req.description);
        t.setValue(req.value);
        t.setDate(req.date);
        t.setUser(u);
        if (req.categoryId != null) {
            Category c = categories.findById(req.categoryId).orElse(null);
            t.setCategory(c);
        }
        return txs.save(t);
    }

    @GetMapping
    public Page<Transaction> list(
        @AuthenticationPrincipal UserDetails ud,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String from,
        @RequestParam(required = false) String to,
        @RequestParam(required = false) BigDecimal min,
        @RequestParam(required = false) BigDecimal max,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        User u = users.findByEmail(ud.getUsername());
        LocalDate f = from != null ? LocalDate.parse(from) : null;
        LocalDate t = to != null ? LocalDate.parse(to) : null;
        Pageable pageable = PageRequest.of(page, size);
        return txs.filter(u, type, f, t, min, max, categoryId, pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ txs.delete(id); }
}
