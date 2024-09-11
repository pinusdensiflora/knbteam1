package com.knbteam1.inuri.news;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class NewsCate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cid;
	
//	@OneToMany(mappedBy = "newsCate", cascade = CascadeType.REMOVE)
//	private List<News> notices;
//	
//	@OneToMany(mappedBy = "newsCate", cascade = CascadeType.REMOVE)
//	private List<News> info;
	
	
	
	

}
