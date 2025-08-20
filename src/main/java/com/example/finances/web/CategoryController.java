package com.example.finances.web;

import com.example.finances.dto.CategoryRequest;
import com.example.finances.model.Category;
import com.example.finances.model.User;
import com.example.finances.service.CategoryService;
import com.example.finances.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categories;
    private final UserService users;

    public CategoryController(CategoryService categories, UserService users){
        this.categories = categories; this.users = users;
    }

    @PostMapping
    public Category create(@Valid @RequestBody CategoryRequest req, @AuthenticationPrincipal UserDetails ud){
        User u = users.findByEmail(ud.getUsername());
        return categories.create(req.name, u);
    }

    @GetMapping
    public List<Category> list(@AuthenticationPrincipal UserDetails ud){
        User u = users.findByEmail(ud.getUsername());
        return categories.listFor(u);
    }
}
