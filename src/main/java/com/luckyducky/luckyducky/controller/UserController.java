package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.Budget;
import com.luckyducky.luckyducky.model.User;
import com.luckyducky.luckyducky.repositories.BudgetRepository;
import com.luckyducky.luckyducky.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String saveUser(@ModelAttribute User user, Model model) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        Budget budget = new Budget("main", 0,user);
        userRepo.save(user);
        budgetRepo.save(budget);

        model.addAttribute("user",user);
        return "user/register-success";
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
}

