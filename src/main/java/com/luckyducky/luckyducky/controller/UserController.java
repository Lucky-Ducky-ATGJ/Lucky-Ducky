package com.luckyducky.luckyducky.controller;

import com.luckyducky.luckyducky.model.*;
import com.luckyducky.luckyducky.repositories.*;
import org.springframework.cglib.core.Local;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Controller
public class UserController {

    // Dependency Injection
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final BudgetRepository budgetRepo;
    private final TransactionRepository transRepo;
    private final CategoryRepository catRepo;
    private final BillRepository billRepo;

    // Springs version of DaoFactory that uses the Repo(interface as a Dao)
    public UserController(PasswordEncoder passwordEncoder, UserRepository userRepo, BudgetRepository budgetRepo, TransactionRepository transRepo, CategoryRepository catRepo, BillRepository billRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.budgetRepo = budgetRepo;
        this.transRepo = transRepo;
        this.catRepo = catRepo;
        this.billRepo = billRepo;
    }

    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String saveUser(Model model, @ModelAttribute User user, HttpServletRequest req, BindingResult bindingResult) {
        String pass = user.getPassword();
        String confirmPass = user.getConfirmPass();
        if (!pass.equals(confirmPass)) {
            model.addAttribute("passConfirm", "passwords do not match");
        } else {
            String hash = passwordEncoder.encode(pass);
            user.setPassword(hash);
            userRepo.save(user);
            authenticate(user);
            Budget budget = new Budget("main", 0, user);
            budgetRepo.save(budget);
        }
            return "redirect:/profile";
    }

    class TxPerCategory {
        public Category cat;
        public int catTotal;
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        // Get Current user and store to model to be sent to HTML
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        // Get a list of the budgets for the current user that isn't their main one (i.e their goal budgets)
        List<Budget> temp = budgetRepo.findBudgetsByUserAndNameIsNot(user, "main");
        model.addAttribute("newGoal", updatedGoalAmounts(temp));
        // Get a list of bills for the current user
        List<Bill> usersBills = billRepo.findBillsByUser(user);
        // Create two empty arrays for the upcoming and overdue bills
        List<Bill> upcomingBills = new ArrayList<>();
        List<Bill> overdueBills = new ArrayList<>();
        // Cycle thru the list of bills for the current user
        for (Bill bill : usersBills) {
            // Get bill's due date and store to a variable
            LocalDate dDate = bill.getDueDate();
            // Get current date and store to a variable
            LocalDate now = LocalDate.now();
            // If the bill is past its due date and is not paid
            // store in overdueBills list
            if (dDate.isBefore(now) && !bill.isPaid()) {
                overdueBills.add(bill);
                // If the bill is not paid and not past the due date
                // store in upcomingBills list
            } else if (dDate.isAfter(now) && !bill.isPaid()) {
                upcomingBills.add(bill);
            }
        }

        // Get all budgets for the current user
        List<Budget> theseBudgets = budgetRepo.findBudgetsByUser(user);
        // Create empty buckets for the transactions, categories and TxPerCategory objects
        List<Transaction> thisUsersTransactions = new ArrayList<>();
        List<Category> thisUsersCategories = new ArrayList<>();
        List<TxPerCategory> categoryTotals = new ArrayList<>();
        // Cycle thru the list of budgets that belong to current user
        for (Budget budget : theseBudgets) {
            // For the current budget get all the transactions associated with it
            List<Transaction> allTx = transRepo.findAllByBudget(budget);
            // Check to make sure Transaction list is not empty
            if (allTx.size() != 0) {
                // If not empty then cycle thru all the transactions in the list
                for (Transaction currentTx : allTx) {
                    // For every Transaction add to bucket list of Transactions (Line 104)
                    thisUsersTransactions.add(currentTx);
                    // For every Transaction check if Associated Category is in the
                        // Bucket list of Categories (Line 105)
                    if (!thisUsersCategories.contains(currentTx.getCategory())) {
                        // If Category is not in bucket list then add it
                        thisUsersCategories.add(currentTx.getCategory());
                    }
                }
            }
        }

        // Cycle thru Bucket list of Categories (Line 105)
        for (Category thisCategory : thisUsersCategories) {
            // Create a new TransCat object
            TxPerCategory thisOne = new TxPerCategory();
            // Add Category to TransCat object and set catTotal to 0
            thisOne.cat = thisCategory;
            thisOne.catTotal = 0;

            // Cycle thru the bucket list of Tranactions
            for (Transaction thisTx : thisUsersTransactions) {
                // Check current Tranaction's category to Current Category id
                if (thisTx.getCategory().getId() == thisCategory.getId()) {
                    // If true then add Transaction amount to catTotal;
                    thisOne.catTotal += thisTx.getAmountInCents();
                }
            }
            // Add Transobject ( Line 130 ) to the bucket list of TransCat objects ( Line 106)
            categoryTotals.add(thisOne);
        } // End of loop

        // add Full bucket list of TransCat objects, list of all upcoming bills, list of all overdue bills
            // to HTML via model
        model.addAttribute("categoryTotals", categoryTotals);
        model.addAttribute("upcomingBills", upcomingBills);
        model.addAttribute("overdueBills", overdueBills);
        // Go to HTML
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

    public List<Budget> updatedGoalAmounts(List<Budget> budgetList) {
        for (Budget budget : budgetList) {
            List<Transaction> transactionList = budget.getTransactions(); // Grab all transactions for each budget object (e.g. goal)
            int bucket = 0;
            if (transactionList.size() != 0) {
                for (Transaction transaction : transactionList) {
                    bucket += transaction.getAmountInCents();
                }
            }
            budget.setGoal_funds(bucket);
        }
        return budgetList;
    }
}