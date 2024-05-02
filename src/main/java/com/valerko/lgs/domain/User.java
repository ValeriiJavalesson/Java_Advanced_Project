package com.valerko.lgs.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table
public class User implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NonNull
	@Column(unique = true)
	private String email;
	@Column
	private String password;
	@Column
	private String firstname;
	@Column
	private String lastname;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	@Lob
	@Column(length = 20971520)
	private String encodedImage;

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getEncodedImage() {
		return encodedImage;
	}

	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, firstname, id, lastname, password, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstname, other.firstname)
				&& Objects.equals(id, other.id) && Objects.equals(lastname, other.lastname)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", role=" + role + "]";
	}

}
