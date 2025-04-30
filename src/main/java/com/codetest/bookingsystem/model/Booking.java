package com.codetest.bookingsystem.model;

import com.codetest.bookingsystem.enums.BookingStatus;
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
@Table(name = "booking")
public class Booking implements Serializable {
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

    private String bookingReferenceNo;

    private Integer noOfSlots;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date pendingDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date refundDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelledDate;

    private Long scheduleClassId;

    private Long userId;

    private String jobId;

    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
}
