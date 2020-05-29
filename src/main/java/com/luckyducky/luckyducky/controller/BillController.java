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
        Bill bill = new Bill();
        model.addAttribute("bills", billRepo.findAll());
        model.addAttribute("categories", cateRepo.findAll());
        model.addAttribute("bill", bill);
        return "bills/index";
    }

    /////////////////  Add Bills  ///////////////////////////
//    @GetMapping("/bills/add")
//    public String addBill(Model model) {
//        Bill bill = new Bill();
//        model.addAttribute("bill", bill);
//        return "bills/add";
//    }

    @PostMapping("/bills/add")
    public String newBill(@ModelAttribute Bill bill) {
        bill.setCreatedAt(new Date());
        billRepo.save(bill);
        return "redirect:/bills";
    }

/////////////////  Edit Bills  //////////////////////////
    @PostMapping("/bills/{id}/edit")
    public String editBill(@ModelAttribute Bill updatedBill,@PathVariable long id){
        updatedBill.setId(id);
        billRepo.save(updatedBill);
        return "redirect:/bills";
    }


/////////////////  Delete Bills  ////////////////////////
    @PostMapping("bills/delete")
    public String deleteBill(@RequestParam long id) {
        Bill bill = billRepo.getOne(id);
        billRepo.delete(bill);
        return "redirect:/bills";
    }
}