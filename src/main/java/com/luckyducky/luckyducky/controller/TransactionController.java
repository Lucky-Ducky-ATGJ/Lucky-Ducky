package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.*;
import com.luckyducky.luckyducky.repositories.BudgetRepository;
import com.luckyducky.luckyducky.repositories.CategoryRepository;
import com.luckyducky.luckyducky.repositories.TransactionRepository;
import com.luckyducky.luckyducky.repositories.UserRepository;
import com.luckyducky.luckyducky.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TransactionController {
    private final TransactionRepository transRepo;
    private final UserRepository userRepo;
    private final CategoryRepository catRepo;
    private final BudgetRepository budgetRepo;
    private final EmailService emailService;

    public TransactionController(TransactionRepository transRepo, UserRepository userRepo, CategoryRepository catRepo, BudgetRepository budgetRepo, EmailService emailService) {
        this.transRepo = transRepo;
        this.userRepo = userRepo;
        this.catRepo = catRepo;
        this.budgetRepo = budgetRepo;


        this.emailService = emailService;
    }

//    @GetMapping("/transactions")
//    public String getAllTransactions(Model model) {
//        model.addAttribute("transactions", transRepo.findAll());
//        model.addAttribute("categories", catRepo.findAll());
//        return "transactions/index";
//    }

    @GetMapping("/transactions")

        public String addTransaction (Model model){
        Transaction transaction = new Transaction();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//       List<Transaction> myBudgetTransactions = budgetRepo.findAllById();
//        System.out.println(myBudgetTransactions);
        model.addAttribute("transactions", transRepo.findAll());
        model.addAttribute("transaction", transaction);
        model.addAttribute("categories", catRepo.findAll());
//        model.addAttribute("isIncome", transaction.getIncome());
        return "transactions/index";
    }


    @PostMapping("/transactions/add")
    public String newTransaction(@ModelAttribute Transaction transaction) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       Budget userBudget = budgetRepo.findBudgetByUserAndName(user, "main");
       transaction.setBudget(userBudget);
       userBudget.getTransactions().add(transaction);
       budgetRepo.save(userBudget);
//       transRepo.save(transaction);
//        transaction.setUser(transaction);
        // Save the new Bill to the database
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

//    @PostMapping("/transactions/{id}/edit")
//    public String editPost(@PathVariable long id, @RequestParam String name, @RequestParam int amount,  @RequestParam Category category,@RequestParam(value = "isIncome", required = false) boolean isIncome,  Model model  ) {
//        Transaction transToEdit = transRepo.getOne(id);
//        System.out.println(transToEdit);
//        model.addAttribute("categories", catRepo.findAll());
//
//        transToEdit.setName(name);
//        transToEdit.setAmountInCents(amount);
//        transToEdit.setIncome(isIncome);
//        transToEdit.setCategory(category);
//
//        transRepo.save(transToEdit);
//
//        return ("redirect:/transactions");
//    }

    @PostMapping("/transactions/edit")
    public String editTransaction(@RequestParam String id, @RequestParam String name, @RequestParam int amount, @RequestParam Category category, @RequestParam(value = "isIncome", required = false) String isIncome, Model model) {
        long parsedId = Long.parseLong(id);
        Transaction transToEdit = transRepo.getOne(parsedId);
        model.addAttribute("categories", catRepo.findAll());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transToEdit.setName(name);
        transToEdit.setAmountInCents(amount);

        if(isIncome != null)
        {
            transToEdit.setIncome(true);
        }
        else
        {
            transToEdit.setIncome(false);
        }
        transToEdit.setCategory(category);
        transRepo.save(transToEdit);
        return "redirect:/transactions";
    }

//    @PostMapping("/transactions/edit")
//    // Use the params from the bills/index HTML in the edit modal
//    public String editTransaction(@RequestParam String id, @RequestParam String name, @RequestParam int amount, @RequestParam Category category, @RequestParam(value = "isIncome", required = false) boolean isIncome, Model model) {
//
//        Transaction transToEdit = transRepo.getOne(id);
////        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        Transaction updatedTransaction = new Transaction(id,name,amount, category, isIncome);
//        // Save and override the bill in the database based on the Id
//        transRepo.save(transToEdit);
//        // Go back to the index of Bills by the URL so that the new info loads
//        return "redirect:/transactions";
//    }
}



