package com.codetest.bookingsystem.model;

import com.codetest.bookingsystem.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 * @Id
	 * 
	 * @Column(name = "id", updatable = false, nullable = false) private UUID id =
	 * UUID.randomUUID();
	 */ //I want to use this uuid unique value

	private String name;

	@NotNull
	@Column(unique = true)
	private String code;

	private Long createdBy;

	@Enumerated(EnumType.STRING)
	private Status status;
}
