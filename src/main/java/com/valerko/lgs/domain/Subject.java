package com.valerko.lgs.domain;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Subject implements Serializable{
	
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String name;
	
}
