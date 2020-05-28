package com.luckyducky.luckyducky.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

@Column(nullable = false)
    private String name;

@OneToMany(cascade = CascadeType.ALL, mappedBy="category")

    private List<Transaction> transactions;

}
