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
<<<<<<< HEAD:inuri/src/main/java/com/knbteam1/inuri/auth/Donation.java
	private Customer customer;
	

=======
	private News imgNews;
>>>>>>> main:inuri/src/main/java/com/knbteam1/inuri/news/Img.java

}
