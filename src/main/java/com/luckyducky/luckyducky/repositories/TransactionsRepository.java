package com.luckyducky.luckyducky.repositories;

import com.luckyducky.luckyducky.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
}
