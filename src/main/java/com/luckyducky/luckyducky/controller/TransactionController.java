package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.*;
import com.luckyducky.luckyducky.repositories.BudgetRepository;
import com.luckyducky.luckyducky.repositories.CategoryRepository;
import com.luckyducky.luckyducky.repositories.TransactionRepository;
import com.luckyducky.luckyducky.repositories.UserRepository;
import com.luckyducky.luckyducky.services.EmailService;
import javassist.runtime.Desc;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @GetMapping("/transactions")


        public String addTransaction (Model model){
//        Transaction transaction = new Transaction();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Budget budget = budgetRepo.findBudgetByUserAndName(user, "main");
//        List<Transaction> transactions = budget.getTransactions();
//        System.out.println(transactions);
//        model.addAttribute("transactions", transactions);
////        model.addAttribute("transactions", transRepo.findAll());
//        model.addAttribute("transaction", transaction);
//        model.addAttribute("categories", catRepo.findAll());
////        model.addAttribute("isIncome", transaction.getIncome());
//        return "transactions/index";


        Transaction transaction = new Transaction();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Budget budget = budgetRepo.findBudgetByUserAndName(user, "main");
        List<Transaction> transactions = budget.getTransactions();

        List<Transaction> ordered = new ArrayList<>();
        if (transactions.size() == 0) {
            ordered = transactions;
        } else {
            for (int i = 0; i < transactions.size() ; i++) {
                System.out.println(transactions.get(i));
                if(i > 0) {
                    if (transactions.get(i).getId() > transactions.get(i - 1).getId()) {
                        ordered.add(0,transactions.get(i));

                    }
                } else{
                    ordered.add(0,transactions.get(i));

                }
            }
        }
        model.addAttribute("transactions", ordered);

        model.addAttribute("transaction", transaction);
        model.addAttribute("categories", catRepo.findAll());
        return "transactions/index";


}

    @PostMapping("/transactions/add")
    public String newTransaction(@ModelAttribute Transaction transaction) {
       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Budget userBudget = budgetRepo.findBudgetByUserAndName(user, "main");
       transaction.setBudget(userBudget);
       transRepo.save(transaction);
       return "redirect:/transactions";
    }

    @PostMapping("/transactions/delete")
    public String deleteTransaction(@RequestParam String id) {
        long parsedId = Long.parseLong(id);
        Transaction transaction = transRepo.getOne(parsedId);
        transRepo.delete(transaction);
        return "redirect:/transactions";
    }

    @PostMapping("/transactions/edit")
    public String editTransaction(@RequestParam String id, @RequestParam String name, @RequestParam int amount, @RequestParam Category category, @RequestParam(value = "isIncome", required = false) String isIncome, Model model) {
        long parsedId = Long.parseLong(id);
        Transaction transToEdit = transRepo.getOne(parsedId);
        model.addAttribute("categories", catRepo.findAll());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transToEdit.setName(name);
        transToEdit.setAmountInCents(amount);

        if (isIncome != null) {
            transToEdit.setIncome(true);
        } else {
            transToEdit.setIncome(false);
        }
        transToEdit.setCategory(category);
        transRepo.save(transToEdit);
        return "redirect:/transactions";
    }

//    @GetMapping("/transactionsss")
//    public String showTransaction(Model model) {
//        Transaction transaction = new Transaction();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Budget budget = budgetRepo.findBudgetByUserAndName(user, "main");
//        List<Transaction> transactions = budget.getTransactions();
//        List<Transaction> ordered = new ArrayList<>();
//        if (transactions.size() == 0) {
//            ordered = transactions;
//        } else {
//            for (int i = 0; i < transactions.size() ; i++) {
//                System.out.println(transactions.get(i));
//                if(i > 0) {
//                    if (transactions.get(i).getId() > transactions.get(i - 1).getId()) {
//                    ordered.add(0,transactions.get(i));
//
//                    }
//                } else{
//                    ordered.add(0,transactions.get(i));
//
//                }
//            }
//        }
//        model.addAttribute("transactions", ordered);
//        model.addAttribute("transaction", transaction);
//        model.addAttribute("categories", catRepo.findAll());
//        return "transactions/transactions";
//    }

}
