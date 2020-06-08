package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.*;
import com.luckyducky.luckyducky.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

import java.time.LocalDate;

import java.util.List;

@Controller
public class BudgetController {
    private final TransactionRepository transRepo;
    private final CategoryRepository cateRepo;
    private final BudgetRepository budgetRepo;

    public BudgetController(TransactionRepository transRepo, CategoryRepository cateRepo, BudgetRepository budgetRepo) {
        this.transRepo = transRepo;
        this.cateRepo = cateRepo;
        this.budgetRepo = budgetRepo;
    }
    @GetMapping("/budget")
    public String showBudget(Model model) { // Moves data from back-end to front-end
        Budget budget = new Budget();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Budget> temp = budgetRepo.findBudgetsByUserAndNameIsNot(user, "main");
        List<Budget> theseBudgets = budgetRepo.findBudgetsByUser(user);
        double thisUsersTransIncome = 0;
        double thisUsersTransExpenses = 0;
        model.addAttribute("budget", budget); // model.attribute ("variable name", "variable value")
        model.addAttribute("transactions", transRepo.findAll());
        model.addAttribute("newGoal", updatedGoalAmounts(temp));
        model.addAttribute("user", user);

        // get transactions with categories​
        for ( Budget userBudget : theseBudgets) {
            List<Transaction> allTx = transRepo.findAllByBudget(userBudget);
            if (allTx.size() != 0) {
                // we got some results, so LET'S GOOOOOOO
                for( Transaction currentTx :  allTx) {
                    if (currentTx.getIncome() == true){
                        thisUsersTransIncome += currentTx.getAmountInCents();
                    }else{
                        thisUsersTransExpenses += currentTx.getAmountInCents();
                    }
                }
            }
        }


        model.addAttribute("income", thisUsersTransIncome);
        model.addAttribute("expenses", thisUsersTransExpenses);

        return "budget/index"; // Sends a Model over page to HTML that contains both "budget" and "transactions" with values
    }

    @GetMapping("/transactions.json")
    public @ResponseBody
        double viewAllTransactionsInJSONFormat() {
        return transRepo.getTotalIncome();
    }

    @GetMapping("/transactions2.json")
    public @ResponseBody
        double viewAllExpendituresInJSONFormat() {
        return transRepo.getTotalExpenditures();
    }

    @GetMapping("/goals.json")
    public @ResponseBody
        double viewAllGoalsInJSONFormat() {
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
    public String addFunds(@RequestParam String goalName, @RequestParam double addedFunds){
        Transaction transaction = new Transaction();
        transaction.setName(goalName);
        transaction.setAmountInCents(addedFunds);
        transaction.setIncome(false); // Add all applicable values for "Transaction" from model
        Category category = cateRepo.getOne(2L); // Will grab category from Category table with id of 2 "Budget Goal"
        transaction.setCategory(category);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Grabs current user
        Budget budget = budgetRepo.findBudgetByUserAndName(user, transaction.getName());
        transaction.setBudget(budget);
        transRepo.save(transaction);
        return "redirect:/budget";
    }

    @GetMapping("/spentbycategory.json")
    public @ResponseBody
    List<Double> viewSpentByCategoryInJSONFormat() {
        List<Double> listOfTotals = transRepo.getTotalExpendituresByCategory();
        return transRepo.getTotalExpendituresByCategory();
    }

    public List<Budget> updatedGoalAmounts(List<Budget> budgetList){
        for(Budget budget : budgetList){
            List<Transaction> transactionList = budget.getTransactions(); // Grab all transactions for each budget object (e.g. goal)
            double bucket = 0;
            if(transactionList.size() != 0){
                for(Transaction transaction : transactionList){
                    bucket += transaction.getAmountInCents();
                }
            }
            budget.setGoal_funds(bucket);
        }
        return budgetList;
    }

    @PostMapping("/goals/edit")
    public String editGoal(@RequestParam long id, @RequestParam String name, @RequestParam double amount){
        Budget budget = budgetRepo.getOne(id);
        budget.setName(name);
        budget.setBalance_in_cents(amount);
        budgetRepo.save(budget);
        return "redirect:/budget";
    }

    @PostMapping("/goals/delete")
    public String deleteGoal(@RequestParam String id) {
        long realId = Long.parseLong(id);
        Budget budget = budgetRepo.getOne(realId);
        budgetRepo.delete(budget);
        return "redirect:/budget";
    }
}
