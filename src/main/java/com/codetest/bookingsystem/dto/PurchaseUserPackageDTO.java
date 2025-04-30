package com.codetest.bookingsystem.dto;

import com.codetest.bookingsystem.embedded.Money;
import lombok.Data;

import java.io.Serializable;

@Data
public class PurchaseUserPackageDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long packageId;
    private Money packagePrice;
}
