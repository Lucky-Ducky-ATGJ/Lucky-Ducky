package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.*;
import com.luckyducky.luckyducky.repositories.BudgetRepository;
import com.luckyducky.luckyducky.repositories.CategoryRepository;
import com.luckyducky.luckyducky.repositories.TransactionRepository;
import com.luckyducky.luckyducky.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    // Dependency Injection
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final BudgetRepository budgetRepo;
    private final TransactionRepository transRepo;
    private final CategoryRepository catRepo;

    // Springs version of DaoFactory that uses the Repo(interface as a Dao)
    public UserController(PasswordEncoder passwordEncoder, UserRepository userRepo, BudgetRepository budgetRepo, TransactionRepository transRepo, CategoryRepository catRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.budgetRepo = budgetRepo;
        this.transRepo = transRepo;
        this.catRepo = catRepo;
    }

    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, HttpServletRequest req, BindingResult bindingResult) {
        String pass = user.getPassword();
            String hash = passwordEncoder.encode(pass);
            user.setPassword(hash);
            userRepo.save(user);
            authenticate(user);
            Budget budget = new Budget("main", 0, user);
            budgetRepo.save(budget);
            return "redirect:/profile";
    }

    class TxPerCategory{
        public Category cat;
        public int catTotal;
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        // get transactions with categories
        List<Budget> theseBudgets = budgetRepo.findBudgetsByUser(user);
        List<Transaction> thisUsersTransactions = new ArrayList<>();
        List<Category> thisUsersCategories = new ArrayList<>();
        List<TxPerCategory> categoryTotals = new ArrayList<>();
        for ( Budget budget : theseBudgets) {
            List<Transaction> allTx = transRepo.findAllByBudget(budget);
            if (allTx.size() != 0) {
                // at this point we should have results
                for( Transaction currentTx : allTx ) {

                    thisUsersTransactions.add(currentTx);

                    if(!thisUsersCategories.contains(currentTx.getCategory())) {
                        thisUsersCategories.add(currentTx.getCategory());
                    }
                }
            }
        }
        // split transactions into arrays for each category that exists
        for ( Category thisCategory : thisUsersCategories ) {
            // iterate through each category this user has assigned to transactions
            TxPerCategory thisOne = new TxPerCategory();
            thisOne.cat = thisCategory;
            thisOne.catTotal = 0;

            for (Transaction thisTx : thisUsersTransactions ) {
                // iterating through all of THIS user's transactions from all categorie
                if (thisTx.getCategory().getId() == thisCategory.getId() ) {
                    thisOne.catTotal += thisTx.getAmountInCents();
                }

            } // done adding all the matching Txs
            categoryTotals.add(thisOne);
        }
        model.addAttribute("categoryTotals", categoryTotals);
        return "user/profile";
    }

    @GetMapping("/profile/{id}/delete")
    public String getDeleteProfileForm(@PathVariable long id, Model model) {
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
    public String getEditProfileForm(@PathVariable long id, Model model) {
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

    private void authenticate(User user) {
        // Notice how we're using an empty list for the roles
        UserDetails userDetails = new UserWithRoles(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
    }
}