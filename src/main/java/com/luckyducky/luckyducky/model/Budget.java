package com.luckyducky.luckyducky.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Value("${some.key:0}")
    private int amount_in_cents;

    @Column
    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Budget(){}

    public Budget(long id, String name, int amount_in_cents, Date deadline) {
        this.id = id;
        this.name = name;
        this.amount_in_cents = amount_in_cents;
        this.deadline = deadline;
    }

    public Budget(String name, int amount_in_cents, Date deadline) {
        this.name = name;
        this.amount_in_cents = amount_in_cents;
        this.deadline = deadline;
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

    public Date getDeadline() {
        return deadline;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

}
