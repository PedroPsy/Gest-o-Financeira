package com.example.finances.service;

import com.example.finances.model.*;
import com.example.finances.repository.TransactionRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo){ this.repo = repo; }

    public Transaction save(Transaction t){ return repo.save(t); }
    public Optional<Transaction> findById(Long id){ return repo.findById(id); }
    public void delete(Long id){ repo.deleteById(id); }

    public Page<Transaction> filter(User user, String type, LocalDate from, LocalDate to,
                                    BigDecimal min, BigDecimal max, Long categoryId, Pageable pageable){
        Specification<Transaction> spec = (root, query, cb) -> {
            var preds = new ArrayList<Predicate>();
            preds.add(cb.equal(root.get("user"), user));
            if (type != null) preds.add(cb.equal(root.type(), type.equals("INCOME") ? Income.class : Expense.class));
            if (from != null) preds.add(cb.greaterThanOrEqualTo(root.get("date"), from));
            if (to != null) preds.add(cb.lessThanOrEqualTo(root.get("date"), to));
            if (min != null) preds.add(cb.greaterThanOrEqualTo(root.get("value"), min));
            if (max != null) preds.add(cb.lessThanOrEqualTo(root.get("value"), max));
            if (categoryId != null) preds.add(cb.equal(root.get("category").get("id"), categoryId));
            return cb.and(preds.toArray(Predicate[]::new));
        };
        return repo.findAll(spec, pageable);
    }
}
