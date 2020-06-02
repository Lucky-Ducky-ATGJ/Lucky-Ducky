package com.luckyducky.luckyducky.controller;


import com.luckyducky.luckyducky.model.Budget;
import com.luckyducky.luckyducky.model.Transaction;
import com.luckyducky.luckyducky.model.User;
import com.luckyducky.luckyducky.repositories.BudgetRepository;
import com.luckyducky.luckyducky.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
public class UserController {

    // Dependency Injection
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final BudgetRepository budgetRepo;

    // Springs version of DaoFactory that uses the Repo(interface as a Dao)
    public UserController(PasswordEncoder passwordEncoder, UserRepository userRepo, BudgetRepository budgetRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.budgetRepo = budgetRepo;
    }

    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, Model model, HttpServletRequest req) {
        String pass = user.getPassword();
        String hash = passwordEncoder.encode(pass);
        user.setPassword(hash);

//        List<Budget> budgets = new ArrayList<>();
//        List<Transaction> transactions = new ArrayList<>();
        Budget budget = new Budget("main", 0,user);
//        budget.setTransactions(transactions);
//        budgets.add(budget);
//        user.setBudgets(budgets);

        userRepo.save(user);

//        UsernamePasswordAuthenticationToken authReq
//                = new UsernamePasswordAuthenticationToken(user.getUsername(), pass);
////        Authentication auth = authManager.authenticate(authReq);
//        Authentication auth = ;
//
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);
//        HttpSession session = req.getSession(true);
//        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/profile/{id}/delete")
    public String getDeleteProfileForm(@PathVariable long id, Model model){
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        User singleUser = userRepo.getOne(id);
        if (user.getId() != user.getId()) {
            return "redirect:/profile/" + user.getId();
        }
        model.addAttribute("user", singleUser);
        return "user/delete";
    }

    @PostMapping("/profile/{id}/delete")
    public String deleteProfile(@PathVariable long id, Model model) {
        User singleUser = userRepo.getOne(id);
        userRepo.delete(singleUser);
        return "redirect:/login";
    }

//    I added the part below, remove it if it doesn't work.
    @GetMapping("/profile/{id}/profile-edit")
    public String getEditProfileForm(@PathVariable long id, Model model){
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        User singleUser = userRepo.getOne(id);

//        User editUser = new User(); //create a new user which will allow me to hold new values
        if (user.getId() != user.getId()) {
            return "redirect:/profile/" + user.getId();
        }
        model.addAttribute("user", singleUser);
//        model.addAttribute("editUser", editUser); //sends updated values to edited user
        return "user/profile-edit";
    }

    @PostMapping("/profile/{id}/profile-edit")
    public String editProfile(@PathVariable long id, @ModelAttribute User user) {
        User tempUser = userRepo.getOne(id);
        user.setPassword(tempUser.getPassword());
        user.setId(id);
        userRepo.save(user);
        return "redirect:/profile";
    }

}