package com.codetest.bookingsystem.interfaces;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.dto.PackageDTO;
import com.codetest.bookingsystem.enums.Status;

import java.util.List;

public interface PackageService {

	PackageDTO createPackage(SessionContext sessionContext, PackageDTO packageDTO);

	PackageDTO getPackageById(SessionContext sessionContext, Long id);

	PackageDTO updatePackage(SessionContext sessionContext, Long id, PackageDTO packageDTO);

	void deletePackage(SessionContext sessionContext, Long id);

	List<PackageDTO> getPackagesByCountryAndStatus(SessionContext sessionContext, Long countryId, Status status);

	List<PackageDTO> getPackagesByStatus(SessionContext sessionContext, Status status);
}
