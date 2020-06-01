package com.luckyducky.luckyducky.repositories;

import com.luckyducky.luckyducky.model.Bill;
import com.luckyducky.luckyducky.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    public List<Bill> findBillsByUser(User user);

}
