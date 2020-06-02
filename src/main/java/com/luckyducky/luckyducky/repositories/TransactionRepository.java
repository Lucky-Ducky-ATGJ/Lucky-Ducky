package com.luckyducky.luckyducky.repositories;

import com.luckyducky.luckyducky.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //    This is a query for getting the total for the Transactions
    @Query("SELECT SUM(t.amountInCents) FROM Transaction t WHERE t.isIncome = true")
    int getTotalIncome();

//        This is a query for getting the total for the Expenditures
    @Query("SELECT SUM(t.amountInCents) FROM Transaction t WHERE t.isIncome = false")
    int getTotalExpenditures();
}
