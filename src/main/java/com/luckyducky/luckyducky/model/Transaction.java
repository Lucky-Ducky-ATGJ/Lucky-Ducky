package com.luckyducky.luckyducky.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction extends Auditable<String>   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Value("${some.key:0}")
    private int amountInCents;

//    @CreatedDate
//    @Column(nullable = true)
//    private Date dateCreated;

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

    public Transaction(long id, String name, int amountInCents, boolean isIncome, Category category) {
        this.id = id;
        this.name = name;
        this.amountInCents = amountInCents;
        this.isIncome = isIncome;
        this.category = category;
    }

    public Transaction(String name, int amountInCents, boolean isIncome, Category category) {
        this.name = name;
        this.amountInCents = amountInCents;
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

    public int getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(int amountInCents) {
        this.amountInCents = amountInCents;
    }

//    public Date getDateCreated() {
//        return dateCreated;
//    }
//
//    public void setDateCreated(Date dateCreated) {
//        this.dateCreated = dateCreated;
//    }

    public Boolean getIncome() {
        return isIncome;
    }

    public void setIncome(Boolean income) {
        isIncome = income;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}



