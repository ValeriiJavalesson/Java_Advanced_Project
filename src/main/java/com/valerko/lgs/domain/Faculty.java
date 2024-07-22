package com.valerko.lgs.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter

public class Faculty implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String name;

	@ManyToMany
	@JoinTable(name = "faculty_subjects", joinColumns = @JoinColumn(name = "faculty_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	private Set<Subject> subjects = new HashSet<Subject>();

	public Faculty() {
	}

	public Faculty(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Faculty [name=" + name + "]";
	}
}
