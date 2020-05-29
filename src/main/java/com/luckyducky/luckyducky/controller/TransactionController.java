package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.Category;
import com.luckyducky.luckyducky.model.Transaction;
import com.luckyducky.luckyducky.repositories.CategoryRepository;
import com.luckyducky.luckyducky.repositories.TransactionRepository;
import com.luckyducky.luckyducky.repositories.UserRepository;
import com.luckyducky.luckyducky.services.EmailService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionController {
    private final TransactionRepository transRepo;
    private final UserRepository userRepo;
    private final CategoryRepository catRepo;
    private final EmailService emailService;


    public TransactionController(TransactionRepository transRepo, UserRepository userRepo, CategoryRepository catRepo, EmailService emailService) {
        this.transRepo = transRepo;
        this.userRepo = userRepo;
        this.catRepo = catRepo;
        this.emailService = emailService;
    }


    @GetMapping("/transactions")
    public String getAllTransactions(Model model) {
        model.addAttribute("transactions", transRepo.findAll());
        return "transactions/index";
    }

    @GetMapping("/transactions/add")
    public String addTransaction(Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        model.addAttribute("categories", catRepo.findAll());
//        model.addAttribute("isIncome", transaction.getIncome());
        return "transactions/transactions";
    }

    @PostMapping("/transactions/add")
    public String newTransaction(@ModelAttribute Transaction transaction) {
        transRepo.save(transaction);
        return "redirect:/transactions";
    }
}



