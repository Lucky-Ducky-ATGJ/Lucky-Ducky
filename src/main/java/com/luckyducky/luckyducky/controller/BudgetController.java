package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BudgetController {
    private final UserRepository userRepo;
    private final TransactionRepository transRepo;
    private final BillRepository billRepo;
    private final CategoryRepository cateRepo;
    private final BudgetRepository budgetRepo;

    public BudgetController(UserRepository userRepo, TransactionRepository transRepo, BillRepository billRepo, CategoryRepository cateRepo, BudgetRepository budgetRepo) {
        this.userRepo = userRepo;
        this.billRepo = billRepo;
        this.transRepo = transRepo;
        this.cateRepo = cateRepo;
        this.budgetRepo = budgetRepo;
    }

    @GetMapping("/budget")
    public String showBudget(Model model) {
        model.addAttribute("transactions", transRepo.findAll());
        return "budget/index";
    }

//    public int totalIncome() {
//        for()
//    }
}
