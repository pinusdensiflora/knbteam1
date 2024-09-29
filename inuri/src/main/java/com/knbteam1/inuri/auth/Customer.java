/* 
Customer.java
생성자: 김가은
생성날짜: 9.11
수정날짜: 9.20
연락처: kkydu007@naver.com
 */

package com.knbteam1.inuri.auth;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


import com.knbteam1.inuri.patron.Donation;

import com.knbteam1.inuri.qna.answer.Answer;
import com.knbteam1.inuri.qna.question.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
public class Customer  implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cid;
	private LocalDateTime cdate;
	

	private String username;
	private String password;
	private boolean enabled;
	private String role;
	
	private String name;
	
	private String postcode;

	private String caddr;
	
	private String ctel;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return isEnabled() == customer.isEnabled() && Objects.equals(getCid(), customer.getCid()) && Objects.equals(getCdate(), customer.getCdate()) && Objects.equals(getUsername(), customer.getUsername()) && Objects.equals(getPassword(), customer.getPassword()) && Objects.equals(getRole(), customer.getRole()) && Objects.equals(getName(), customer.getName()) && Objects.equals(getPostcode(), customer.getPostcode()) && Objects.equals(getCaddr(), customer.getCaddr()) && Objects.equals(getCtel(), customer.getCtel());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCid(), getCdate(), getUsername(), getPassword(), isEnabled(), getRole(), getName(), getPostcode(), getCaddr(), getCtel());
	}

	@ToString.Exclude // Exclude the child from toString() to prevent recursion
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	private List<Donation> donation;

	@ToString.Exclude // Exclude the child from toString() to prevent recursion
	@OneToMany(mappedBy = "aauthor", cascade = CascadeType.REMOVE)
	private List<Answer> answers;

	@ToString.Exclude // Exclude the child from toString() to prevent recursion
	@OneToMany(mappedBy = "qauthor", cascade = CascadeType.REMOVE)
	private List<Question> questions;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

	@Override
	public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

	@Override
	public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

	public static Customer of(String username, String password, String name, String addr, String postcode, String ctel) {
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setName(name);
		customer.setCaddr(addr);
		customer.setPostcode(postcode);
		customer.setCtel(ctel);

		return customer;
	}

	@PrePersist
	private void prePersist(){
		this.cdate = LocalDateTime.now();
	}

}
