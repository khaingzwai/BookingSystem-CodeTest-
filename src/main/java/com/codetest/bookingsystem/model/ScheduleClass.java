package com.codetest.bookingsystem.model;

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
@Table(name = "schedule_class")
public class ScheduleClass implements Serializable {
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
    private Integer maxSlots;

    private Integer noOfUsedSlots;

    @Temporal(TemporalType.DATE)
    private Date classDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private String address;
    private String lecturerName;
    private String phNo;
    private Integer noOfCredits;
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

}
