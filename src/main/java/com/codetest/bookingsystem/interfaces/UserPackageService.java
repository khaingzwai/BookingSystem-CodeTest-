package com.codetest.bookingsystem.interfaces;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.dto.PurchaseResponseDTO;
import com.codetest.bookingsystem.dto.PurchaseUserPackageDTO;
import com.codetest.bookingsystem.dto.UserPackageDTO;

import java.util.List;

public interface UserPackageService {
	UserPackageDTO getUserPackageById(SessionContext sessionContext, Long id);

	PurchaseResponseDTO purchaseUserPackage(SessionContext sessionContext, Long userId,
			PurchaseUserPackageDTO purchaseUserPackageDTO);

	List<UserPackageDTO> getAllUserPackagesByUserId(SessionContext sessionContext, Long userId);

	UserPackageDTO updateUserPackageClass(SessionContext sessionContext, Long id, UserPackageDTO userPackageDTO);

	List<UserPackageDTO> getAllUserPackages(SessionContext sessionContext);
}
