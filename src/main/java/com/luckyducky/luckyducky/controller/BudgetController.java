package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.*;
import com.luckyducky.luckyducky.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("budget", budget); // model.attribute ("variable name", "variable value")
        model.addAttribute("transactions", transRepo.findAll());
        model.addAttribute("newGoal", updatedGoalAmounts(temp));
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

    public List<Budget> updatedGoalAmounts(List<Budget> budgetList){
        for(Budget budget : budgetList){
            List<Transaction> transactionList = budget.getTransactions(); // Grab all transactions for each budget object (e.g. goal)
            int bucket = 0;
            if(transactionList.size() != 0){
                for(Transaction transaction : transactionList){
                    bucket += transaction.getAmountInCents();
                }
            }
            budget.setGoal_funds(bucket);
        }
        return budgetList;
    }

//    @PostMapping("/goals/edit")
//    public String editGoal(@RequestParam long id, @RequestParam String name, @RequestParam int amount){
//        Budget budget = budgetRepo.getOne(id);
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Budget updatedBudget = new Budget(id,name,amount,user);
//        budgetRepo.save(updatedBudget);
//        return "redirect:/budget";
//    }


    @PostMapping("/goals/delete")
    public String deleteGoal(@RequestParam String id) {
        long realId = Long.parseLong(id);
        Budget budget = budgetRepo.getOne(realId);
        budgetRepo.delete(budget);
        return "redirect:/budget";
    }
}
