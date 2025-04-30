package com.codetest.bookingsystem.model;

import com.codetest.bookingsystem.embedded.Money;
import com.codetest.bookingsystem.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "package_table")
public class Packages implements Serializable {
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

    private Integer noOfAvailableDays;

    @Embedded
    private Money costOfAmount;

    private Integer noOfAvailableCredits;

    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Status status;
}
