package com.luckyducky.luckyducky.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    @Value("${some.key:0}")
    private int amount_in_cents;

    @Column(nullable = false)
    private Date dueDate;

    @Column(nullable = false)
    @Value("${some.key:false}")
    private Boolean isIncome;

    @Column(nullable = false)
    @Value("${some.key:false}")
    private Boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Bill(){};

    public Bill(long id, String name, String company, int amount_in_cents, Date dueDate) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.amount_in_cents = amount_in_cents;
        this.dueDate = dueDate;
    }

    public Bill(String name, String company, int amount_in_cents, Date dueDate) {
        this.name = name;
        this.company = company;
        this.amount_in_cents = amount_in_cents;
        this.dueDate = dueDate;
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

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public int getAmount_in_cents() {
        return amount_in_cents;
    }
    public void setAmount_in_cents(int amount_in_cents) {
        this.amount_in_cents = amount_in_cents;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIncome() {
        return isIncome;
    }
    public void setIncome(Boolean income) {
        isIncome = income;
    }

    public Boolean getPaid() {
        return isPaid;
    }
    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
