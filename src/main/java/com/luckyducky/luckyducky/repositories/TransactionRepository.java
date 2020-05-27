package com.luckyducky.luckyducky.repositories;

import com.luckyducky.luckyducky.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
