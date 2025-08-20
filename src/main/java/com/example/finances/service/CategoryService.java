package com.example.finances.service;

import com.example.finances.model.Category;
import com.example.finances.model.User;
import com.example.finances.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repo;
    public CategoryService(CategoryRepository repo){ this.repo = repo; }

    public Category create(String name, User user){
        Category c = new Category(name, user);
        return repo.save(c);
    }
    public List<Category> listFor(User user){ return repo.findByUserOrUserIsNull(user); }
}
