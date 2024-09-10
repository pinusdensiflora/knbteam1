package user;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//package com.mysite
public class SiteUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

}
