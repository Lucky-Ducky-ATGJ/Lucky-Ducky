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
import java.util.*;

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

    static class TxPerCategory {
        public Category cat;
        public int catTotal;
    }

    @GetMapping("/transactions")        
    public String showTransaction(Model model) {
        Transaction transaction = new Transaction();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Budget> budgets = budgetRepo.findBudgetsByUser(user);
        List<Transaction> ordered = combineTransactions(budgets);
        double amount = transaction.getAmountInCents();
        model.addAttribute("amount", amount);
        model.addAttribute("transactions", ordered);
        model.addAttribute("transaction", transaction);
        model.addAttribute("categories", catRepo.findAll());


        List<Budget> theseBudgets = budgetRepo.findBudgetsByUser(user);
        List<Transaction> thisUsersTransactions = new ArrayList<>();
        List<Category> thisUsersCategories = new ArrayList<>();
        List<UserController.TxPerCategory> categoryTotals = new ArrayList<>();
        for (Budget budget : theseBudgets) {
            List<Transaction> allTx = transRepo.findAllByBudget(budget);
            if (allTx.size() != 0) {
                for (Transaction currentTx : allTx) {
                    thisUsersTransactions.add(currentTx);
                    if (!thisUsersCategories.contains(currentTx.getCategory())) {
                        thisUsersCategories.add(currentTx.getCategory());
                    }
                }
            }
        }
        for (Category thisCategory : thisUsersCategories) {
            UserController.TxPerCategory thisOne = new UserController.TxPerCategory();
            thisOne.cat = thisCategory;
            thisOne.catTotal = 0;
            for (Transaction thisTx : thisUsersTransactions) {
                if (thisTx.getCategory().getId() == thisCategory.getId()) {
                    thisOne.catTotal += thisTx.getAmountInCents();
                }
            }
            categoryTotals.add(thisOne);
        }
        model.addAttribute("categoryTotals", categoryTotals);

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
    public String editTransaction(@RequestParam String id, @RequestParam String name, @RequestParam double amount, @RequestParam Category category, @RequestParam(value = "isIncome", required = false) String isIncome, Model model) {
        long parsedId = Long.parseLong(id);
        Transaction transToEdit = transRepo.getOne(parsedId);
        model.addAttribute("categories", catRepo.findAll());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/transactions/edit";
    }


    public List<Transaction> combineTransactions(List<Budget> budgets) {
        List<Transaction> transactions = new ArrayList<>();
        for (Budget budget : budgets) {
            List<Transaction> temp = budget.getTransactions();
            if (temp.size() != 0) {
                for (Transaction transaction : temp) {
                    transactions.add(0, transaction);
                }
            }
        }
        Comparator<Transaction> compareById = (Transaction t1, Transaction t2) -> (int) (t1.getId() - t2.getId());
        transactions.sort(compareById.reversed());
        return transactions;
    }
}
