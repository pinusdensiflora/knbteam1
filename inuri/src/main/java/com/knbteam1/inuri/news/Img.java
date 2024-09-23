package com.knbteam1.inuri.news;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Img {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iid;

	private LocalDateTime idate;
	
	private String ilink;
	
	@ManyToOne
	News news;

}
