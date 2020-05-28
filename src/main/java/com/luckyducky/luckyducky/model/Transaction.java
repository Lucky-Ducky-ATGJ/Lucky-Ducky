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
    private int amount_in_cents;

    @Column(nullable = false)
    private Date dateCreated;

    @Column(nullable = false)
    @Value("${some.key:false}")
    private Boolean isIncome;

    @OneToOne
    private Category category;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    public Transaction() {
    }

    public Transaction(long id, String name, int amount_in_cents, Date dateCreated, boolean isIncome, Category category) {
        this.id = id;
        this.name = name;
        this.amount_in_cents = amount_in_cents;
        this.dateCreated = dateCreated;
        this.isIncome = isIncome;
        this.category = category;
    }

    public Transaction(String name, int amount_in_cents, Date dateCreated, boolean isIncome, Category category) {
        this.name = name;
        this.amount_in_cents = amount_in_cents;
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
        return amount_in_cents;
    }

    public void setAmount_in_cents(int amount_in_cents) {
        this.amount_in_cents = amount_in_cents;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
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



