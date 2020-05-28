package com.luckyducky.luckyducky.model;

import javax.persistence.*;

@Entity
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

@Column(nullable = false)
    private String name;

@OneToOne
    private Transaction transaction;

}
