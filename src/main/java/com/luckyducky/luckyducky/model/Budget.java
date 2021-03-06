package com.luckyducky.luckyducky.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Value("${some.key:0}")
    private double balance_in_cents;

    @Column
    private double goal_funds;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budget")
    private List<Transaction> transactions;


    public Budget(){}

    public Budget(long id, String name, double balance_in_cents, User user) {
        this.id = id;
        this.name = name;
        this.balance_in_cents = balance_in_cents;
        this.user = user;
    }

    public Budget(String name, double balance_in_cents, User user) {
        this.name = name;
        this.balance_in_cents = balance_in_cents;
        this.user = user;
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

    public double getBalance_in_cents() {
        return balance_in_cents;
    }
    public void setBalance_in_cents(double balance_in_cents) {
        this.balance_in_cents = balance_in_cents * 100;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getGoal_funds() {
        return goal_funds;
    }
    public void setGoal_funds(double goal_funds) {
        this.goal_funds = goal_funds;
    }
}
