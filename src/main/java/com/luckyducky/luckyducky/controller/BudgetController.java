package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.Budget;
import com.luckyducky.luckyducky.model.Category;
import com.luckyducky.luckyducky.model.Transaction;
import com.luckyducky.luckyducky.model.User;
import com.luckyducky.luckyducky.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String showBudget(Model model) { // Moves data from back-end to front-end
        Budget budget = new Budget();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("budget", budget); // model.attribute ("variable name", "variable value")
        model.addAttribute("transactions", transRepo.findAll());
        model.addAttribute("newGoal", budgetRepo.findBudgetsByUserAndNameIsNot(user, "main"));
        return "budget/index"; // Sends a Model over page to HTML that contains both "budget" and "transactions" with values
    }

    @GetMapping("/transactions.json")
    public @ResponseBody
        int viewAllTransactionsInJSONFormat() {
        return transRepo.getTotalIncome();
    }

    @GetMapping("/transactions2.json")
    public @ResponseBody
        int viewAllExpendituresInJSONFormat() {
        return transRepo.getTotalExpenditures();
    }

    @GetMapping("/goals.json")
    public @ResponseBody
    int viewAllGoalsInJSONFormat() {
        return transRepo.getGoalTotal();
    }

    @PostMapping("/addGoal")
    public String addGoal(@ModelAttribute Budget budget){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Grabs current user
        budget.setUser(user);
        budgetRepo.save(budget);
        return "redirect:/budget";
    }

    @PostMapping("/addFunds")
    public String addFunds(@RequestParam String goalName, @RequestParam int addedFunds){
        Transaction transaction = new Transaction();
        transaction.setName(goalName);
        transaction.setAmountInCents(addedFunds);
        transaction.setIncome(false); // Add all applicable values for "Transaction" from model
        Category category = cateRepo.getOne(14L); // Will grab category from Category table with id of 14 "Budget Goal"
        transaction.setCategory(category);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Grabs current user
        Budget budget = budgetRepo.findBudgetByUserAndName(user, transaction.getName());
        transaction.setBudget(budget);
        transRepo.save(transaction);
        return "redirect:/budget";
    }

@GetMapping("/spentbycategory.json")
    public @ResponseBody
    List<Integer> viewSpentByCategoryInJSONFormat() {
        List<Integer> listOfTotals = transRepo.getTotalExpendituresByCategory();
        return transRepo.getTotalExpendituresByCategory();
    }
}