package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.repositories.BillRepository;
import com.luckyducky.luckyducky.repositories.CategoryRepository;
import com.luckyducky.luckyducky.repositories.TransactionRepository;
import com.luckyducky.luckyducky.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BillController {
    private final UserRepository userRepo;
    private final TransactionRepository transRepo;
    private final BillRepository billRepo;
    private final CategoryRepository cateRepo;

    public BillRepository(UserRepository userRepo, TransactionRepository transRepo, BillRepository billRepo, CategoryRepository cateRepo){
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
}
