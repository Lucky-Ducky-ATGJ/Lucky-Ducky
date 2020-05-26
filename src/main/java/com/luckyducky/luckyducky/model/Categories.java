package com.luckyducky.luckyducky.model;

import javax.persistence.*;

@Entity
public class Categories {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

@Column(nullable = false)
    private String name;

}
