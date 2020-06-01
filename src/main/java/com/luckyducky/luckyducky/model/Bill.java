package com.luckyducky.luckyducky.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Entity
public class Bill{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private int amountInCents;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate dueDate;

    @Column
    @Value("${some.key:false}")
    private boolean isPaid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
    private List<Transaction> transactions;

    public Bill() {
    }

    public Bill(long id, String name, int amountInCents, LocalDate dueDate, boolean isPaid, Date createdAt,  User user, List<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.amountInCents = amountInCents;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
        this.createdAt = createdAt;
        this.user = user;
        this.transactions = transactions;
    }

    public Bill(String name, int amountInCents, LocalDate dueDate, boolean isPaid, User user, List<Transaction> transactions) {
        this.name = name;
        this.amountInCents = amountInCents;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
        this.user = user;
        this.transactions = transactions;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
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

}
