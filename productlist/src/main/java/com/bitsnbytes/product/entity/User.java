package com.bitsnbytes.product.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
