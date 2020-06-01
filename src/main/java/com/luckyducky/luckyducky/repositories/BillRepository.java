package com.luckyducky.luckyducky.repositories;

import com.luckyducky.luckyducky.model.Bill;
import com.luckyducky.luckyducky.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
