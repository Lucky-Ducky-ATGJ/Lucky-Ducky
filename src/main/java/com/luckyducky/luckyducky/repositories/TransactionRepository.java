package com.luckyducky.luckyducky.repositories;

import com.luckyducky.luckyducky.model.Budget;
import com.luckyducky.luckyducky.model.Transaction;
import com.luckyducky.luckyducky.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

////  This is a query for getting the total for Transactions
//    @Query("SELECT SUM(t.amountInCents) FROM Transaction t JOIN Budget b ON b.id = t.id WHERE t.isIncome = true")
//    double getTotalIncome();
//
////  This is a query for getting the total for Expenditures
//    @Query("SELECT SUM(t.amountInCents) FROM Transaction t WHERE t.isIncome = false")
//    double getTotalExpenditures();
//
////  Query to get total of expenditures by category
////      This is the query that works in SequelPro -->>  ("SELECT SUM(amount_in_cents) FROM Transaction WHERE is_income = false GROUP BY category_id")
//    @Query("SELECT SUM(t.amountInCents) FROM Transaction t WHERE t.isIncome = false GROUP BY t.category")
//    List<Double> getTotalExpendituresByCategory();
//
////  This is a query for getting the total for a Goal
//    @Query("SELECT SUM(t.amountInCents) FROM Transaction t WHERE t.budget = '2'")
//    double getGoalTotal();


    // get all transactions by budget ID - used for the code Casey advised to use to get db info as objects
    public List<Transaction> findAllByBudget(Budget budget);

    // Get all transactions by budget for a set time period
    public List<Transaction> findAllByBudgetAndCategory_IdAndCreateDateIsBetween(Budget budget, long id, Date start, Date end);
}


