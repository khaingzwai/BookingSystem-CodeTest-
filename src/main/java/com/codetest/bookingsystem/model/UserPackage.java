package com.codetest.bookingsystem.model;

import com.codetest.bookingsystem.embedded.Money;
import com.codetest.bookingsystem.enums.UserPackageStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_package")
public class UserPackage implements Serializable {
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

    private String packageName;//copy of package name

    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;

    @Embedded
    private Money costOfAmount;

    private Integer noOfCredits;

    private Integer noOfRemainCredits;

    @Enumerated(EnumType.STRING)
    private UserPackageStatus userPackageStatus;

    private Long packageId;
    private Long countryId;
    private Long userId;
}
