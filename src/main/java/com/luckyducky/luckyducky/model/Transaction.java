package com.luckyducky.luckyducky.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Value("${some.key:0}")
    private int amountInCents;

    @Column(nullable = false)
    private Date dateCreated;

    @Column(nullable = false)
    @Value("${some.key:false}")
    private Boolean isIncome;

    @ManyToOne
    @JoinColumn(name= "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    public Transaction() {
    }

    public Transaction(long id, String name, int amountInCents, Date dateCreated, boolean isIncome, Category category) {
        this.id = id;
        this.name = name;
        this.amountInCents = amountInCents;
        this.dateCreated = dateCreated;
        this.isIncome = isIncome;
        this.category = category;
    }

    public Transaction(String name, int amountInCents, Date dateCreated, boolean isIncome, Category category) {
        this.name = name;
        this.amountInCents = amountInCents;
        this.dateCreated = dateCreated;
        this.isIncome = isIncome;
        this.category = category;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount_in_cents() {
        return amountInCents;
    }

    public void setAmount_in_cents(int amountInCents) {
        this.amountInCents = amountInCents;
    }

    public Date getdateCreated() {
        return dateCreated;
    }

    public void setdateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIncome() {
        return isIncome;
    }

    public void setIncome(Boolean income) {
        isIncome = income;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}



