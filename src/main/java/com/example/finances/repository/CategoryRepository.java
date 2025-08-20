package com.example.finances.repository;

import com.example.finances.model.Category;
import com.example.finances.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserOrUserIsNull(User user);
}
