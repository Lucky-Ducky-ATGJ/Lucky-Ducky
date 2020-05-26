package com.luckyducky.luckyducky.repositories;

import com.luckyducky.luckyducky.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

}
