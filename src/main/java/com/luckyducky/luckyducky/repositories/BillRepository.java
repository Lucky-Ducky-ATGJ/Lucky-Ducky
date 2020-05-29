package com.luckyducky.luckyducky.repositories;

import com.luckyducky.luckyducky.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
