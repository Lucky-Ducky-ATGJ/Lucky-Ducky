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
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
public class BillController {
    private final UserRepository userRepo;
    private final TransactionRepository transRepo;
    private final BillRepository billRepo;
    private final CategoryRepository cateRepo;

    public BillController(UserRepository userRepo, TransactionRepository transRepo, BillRepository billRepo, CategoryRepository cateRepo) {
        this.userRepo = userRepo;
        this.billRepo = billRepo;
        this.transRepo = transRepo;
        this.cateRepo = cateRepo;
    }

/////////////////  Show Bills  //////////////////////////
    @GetMapping("/bills")
    public String showBill(Model model) {
        model.addAttribute("bills", billRepo.findAll());
        model.addAttribute("categories", cateRepo.findAll());
        return "bills/index";
    }

/////////////////  Add Bills  ///////////////////////////
    @PostMapping("/bills/add")
    public String newBill(@ModelAttribute Bill bill) {
        bill.setCreatedAt(new Date());
        billRepo.save(bill);
        return "redirect:/bills";
    }

/////////////////  Edit Bills  //////////////////////////
    @PostMapping("/bills/edit")
    public String editBill(@RequestParam long id, @RequestParam String name, @RequestParam String date, @RequestParam int amount){
        LocalDate lDate = LocalDate.parse(date);
        Bill bill = billRepo.getOne(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Bill updatedBill = new Bill(id,name,amount,lDate,bill.isPaid(),bill.getCreatedAt(),bill.getModifiedAt(), user, bill.getTransactions());
        billRepo.save(updatedBill);
        return "redirect:/bills";
    }


/////////////////  Delete Bills  ////////////////////////
    @PostMapping("/bills/delete")
    public String deleteBill(@RequestParam long id) {
        Bill bill = billRepo.getOne(id);
        billRepo.delete(bill);
        return "redirect:/bills";
    }
}