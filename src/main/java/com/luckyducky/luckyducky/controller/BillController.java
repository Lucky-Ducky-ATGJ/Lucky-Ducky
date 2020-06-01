package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.Bill;
import com.luckyducky.luckyducky.model.Transaction;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        // Creates empty Bill to send to HTML
        Bill bill = new Bill();
        // Get List of bills by user

        // Send model with list of bills
        model.addAttribute("bills", billRepo.findAll());
        model.addAttribute("categories", cateRepo.findAll());
        model.addAttribute("newBill", bill);
        return "bills/index";
    }

/////////////////  Add Bills  ///////////////////////////
    @PostMapping("/bills/add")
    public String newBill(@ModelAttribute Bill bill) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Transaction> transactions = new ArrayList<>();
        bill.setPaid(false);
        bill.setUser(user);
        bill.setTransactions(transactions);
        billRepo.save(bill);
        return "redirect:/bills";
    }

/////////////////  Edit Bills  //////////////////////////
    @PostMapping("/bills/edit")
    public String editBill(@RequestParam long id, @RequestParam String name, @RequestParam String date, @RequestParam int amount){
        LocalDate lDate = LocalDate.parse(date);
        Bill bill = billRepo.getOne(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Bill updatedBill = new Bill(id,name,amount,lDate,bill.isPaid(),bill.getCreatedAt(),user,bill.getTransactions());
        billRepo.save(updatedBill);
        return "redirect:/bills";
    }


/////////////////  Delete Bills  ////////////////////////
    @PostMapping("/bills/delete")
    public String deleteBill(@RequestParam String id) {
        long realId = Long.parseLong(id);
        Bill bill = billRepo.getOne(realId);
        billRepo.delete(bill);
        return "redirect:/bills";
    }
}