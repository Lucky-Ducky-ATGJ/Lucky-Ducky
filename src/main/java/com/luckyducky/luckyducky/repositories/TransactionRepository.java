package com.luckyducky.luckyducky.repositories;

import com.luckyducky.luckyducky.model.Transaction;
import com.luckyducky.luckyducky.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    List<Transaction> findTransactionsByUser(User user);
}
