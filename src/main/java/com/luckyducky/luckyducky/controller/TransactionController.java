package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.Bill;
import com.luckyducky.luckyducky.model.Category;
import com.luckyducky.luckyducky.model.Transaction;
import com.luckyducky.luckyducky.model.User;
import com.luckyducky.luckyducky.repositories.CategoryRepository;
import com.luckyducky.luckyducky.repositories.TransactionRepository;
import com.luckyducky.luckyducky.repositories.UserRepository;
import com.luckyducky.luckyducky.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Date;

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

//    @GetMapping("/transactions")
//    public String getAllTransactions(Model model) {
//        model.addAttribute("transactions", transRepo.findAll());
//        model.addAttribute("categories", catRepo.findAll());
//        return "transactions/index";
//    }

    @GetMapping("/transactions")
    public String addTransaction(Model model) {
        Transaction transaction = new Transaction();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("transactions", transRepo.findAll());
        model.addAttribute("transaction", transaction);
        model.addAttribute("categories", catRepo.findAll());
//        model.addAttribute("isIncome", transaction.getIncome());
        return "transactions/index";
    }

    @PostMapping("/transactions/add")
    public String newTransaction(@ModelAttribute Transaction transaction) {

        Category cat = transaction.getCategory();
        transRepo.save(transaction);
        transaction.setUser(transaction);
        // Save the new Bill to the database
        transRepo.save(transaction);
        return "redirect:/transactions";
    }

//    @PostMapping("/transactions/{id}/delete")
//    public String deleteTransaction(@PathVariable long id) {
//        transRepo.deleteById(id);
//        return "redirect:/transactions";
//    }

    @PostMapping("/transactions/delete")
    public String deleteTransaction(@RequestParam String id) {
        long parsedId = Long.parseLong(id);
        Transaction transaction = transRepo.getOne(parsedId);
        transRepo.delete(transaction);
        return "redirect:/transactions";
    }

//    @GetMapping("/transactions/{id}/edit")
//    public String showEditPost(@PathVariable long id, Model model) {
//        Transaction editedTransaction = transRepo.getOne(id);
//        model.addAttribute("categories", catRepo.findAll());
//        model.addAttribute("transaction", editedTransaction);
//        return ("/transactions/transactions");
//    }

    @PostMapping("/transactions/{id}/edit")
    public String editPost(@PathVariable long id, @RequestParam String name, @RequestParam int amount,  @RequestParam Category category,@RequestParam(value = "isIncome", required = false) boolean isIncome,  Model model  ) {
        Transaction transToEdit = transRepo.getOne(id);
        System.out.println(transToEdit);
        model.addAttribute("categories", catRepo.findAll());

        transToEdit.setName(name);
        transToEdit.setAmountInCents(amount);
        transToEdit.setIncome(isIncome);
        transToEdit.setCategory(category);

        transRepo.save(transToEdit);

        return ("redirect:/transactions");
    }

    @PostMapping("/transactions/edit")
    public String editTransaction(@RequestParam long id, @RequestParam String name, @RequestParam int amount, @RequestParam Category category, @RequestParam(value = "isIncome", required = false) boolean isIncome, Model model) {
        model.addAttribute("categories", catRepo.findAll());
        Transaction transaction = transRepo.getOne(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Transaction updatedTransaction = new Transaction(id, name, amount, isIncome, category);
        transRepo.save(updatedTransaction);
        return "redirect:/bills";
    }
}



