package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.Bill;
import com.luckyducky.luckyducky.model.User;
import com.luckyducky.luckyducky.repositories.BillRepository;
import com.luckyducky.luckyducky.repositories.CategoryRepository;
import com.luckyducky.luckyducky.repositories.TransactionRepository;
import com.luckyducky.luckyducky.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
public class BillController {
    private final UserRepository userRepo;
    private final TransactionRepository transRepo;
    private final BillRepository billRepo;
    private final CategoryRepository cateRepo;

    public BillController(UserRepository userRepo, TransactionRepository transRepo, BillRepository billRepo, CategoryRepository cateRepo){
        this.userRepo = userRepo;
        this.billRepo = billRepo;
        this.transRepo = transRepo;
        this.cateRepo = cateRepo;
    }

/////////////////  Show Bills  //////////////////////////
    @GetMapping("/profile/bills")
    public String showBill(Model model){
        model.addAttribute("bills", billRepo.findAll());
        model.addAttribute("categories", cateRepo.findAll());
        return "bills/index";
    }

/////////////////  Add Bills  ///////////////////////////
    @GetMapping("/profile/bills/add")
    public String addBill(Model model){
        Bill bill = new Bill();
        model.addAttribute("bill",bill);
        return "bills/add";
    }

    @PostMapping("/profile/bills/add")
    public String newBill(@ModelAttribute Bill bill){
        bill.setCreatedAt(new Date());
        billRepo.save(bill);
        return "redirect:/profile/bills";
    }
}
