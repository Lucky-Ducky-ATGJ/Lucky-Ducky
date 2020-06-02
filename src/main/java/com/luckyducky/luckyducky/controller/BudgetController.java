package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.Transaction;
import com.luckyducky.luckyducky.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String showBudget(Model model) {
        model.addAttribute("transactions", transRepo.findAll());
        return "budget/index";
    }

//    @GetMapping("/budget/incomeImport.json")
//    public String getIncomeTotal(Model model){
//        int totalIncome = transRepo.getTotalIncome();
//        model.addAttribute("incomeTotal", totalIncome);
//        return "budget/index";
//    }

    @GetMapping("/transactions.json")
    public @ResponseBody
        int viewAllTransactionsInJSONFormat() {
        return transRepo.getTotalIncome();
    }

//    @GetMapping("/budget/expenditureImport.json")
//    public String getExpenditureTotal(Model model){
//        int totalExpenditures = transRepo.getTotalExpenditures();
//        model.addAttribute("expenditureTotal", totalExpenditures);
//        return "budget/index";
//    }
//
    @GetMapping("/transactions2.json")
    public @ResponseBody
        int viewAllExpendituresInJSONFormat() {
        return transRepo.getTotalExpenditures();
    }

    @GetMapping("/spentbycategory.json")
    public @ResponseBody
    int viewSpentByCategoryInJSONFormat() {
        return transRepo.getTotalExpendituresByCategory();
    }
}