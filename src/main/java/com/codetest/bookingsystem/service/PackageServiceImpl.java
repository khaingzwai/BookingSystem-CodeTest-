package com.codetest.bookingsystem.service;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.dto.PackageDTO;
import com.codetest.bookingsystem.enums.Status;
import com.codetest.bookingsystem.interfaces.PackageService;
import com.codetest.bookingsystem.mapper.PackageMapper;
import com.codetest.bookingsystem.model.Country;
import com.codetest.bookingsystem.model.Packages;
import com.codetest.bookingsystem.repository.CountryRepository;
import com.codetest.bookingsystem.repository.PackageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl implements PackageService {

	@Autowired
	private PackageRepository packageRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Override
	@Transactional
	public PackageDTO createPackage(SessionContext sessionContext, PackageDTO packageDTO) {
		try {
			Packages packageEntity = PackageMapper.toEntity(packageDTO);
			if (packageDTO.getCountryDTO() != null) {
				Optional<Country> country = countryRepository.findById(packageDTO.getCountryDTO().getId());
				packageEntity.setCountry(country.get());
			}
			packageEntity.setStatus(Status.VALID);
			Packages savedPackage = packageRepository.save(packageEntity);
			return PackageMapper.toDTO(savedPackage);
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public PackageDTO getPackageById(SessionContext sessionContext, Long id) {
		try {
			Packages packageEntity = packageRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Package not found"));
			return PackageMapper.toDTO(packageEntity);
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public PackageDTO updatePackage(SessionContext sessionContext, Long id, PackageDTO packageDTO) {
		try {
			Packages existingPackage = packageRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Package not found"));
			if (packageDTO.getCountryDTO() != null) {
				Country country = countryRepository.findById(packageDTO.getCountryDTO().getId())
						.orElseThrow(() -> new RuntimeException("Country not found"));
				existingPackage.setCountry(country);
			}
			existingPackage.setName(packageDTO.getName());
			existingPackage.setNoOfAvailableDays(packageDTO.getNoOfAvailableDays());
			existingPackage.setCostOfAmount(packageDTO.getCostOfAmount());
			existingPackage.setNoOfAvailableCredits(packageDTO.getNoOfAvailableCredits());
			existingPackage.setDescription(packageDTO.getDescription());
			if (packageDTO.getStatus() != null) {
				existingPackage.setStatus(packageDTO.getStatus());
			}

			Packages updatedPackage = packageRepository.save(existingPackage);
			return PackageMapper.toDTO(updatedPackage);
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void deletePackage(SessionContext sessionContext, Long id) {
		try {
			if (!packageRepository.existsById(id)) {
				throw new RuntimeException("Package not found");
			}
			packageRepository.deleteById(id);
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<PackageDTO> getPackagesByCountryAndStatus(SessionContext sessionContext, Long countryId,
			Status status) {
		try {
			return packageRepository.findAll().stream()
					.filter(pkg -> pkg.getCountry().getId().equals(countryId) && pkg.getStatus() == status)
					.map(PackageMapper::toDTO).collect(Collectors.toList());
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<PackageDTO> getPackagesByStatus(SessionContext sessionContext, Status status) {
		try {
			return packageRepository.findAll().stream().filter(pkg -> pkg.getStatus() == status)
					.map(PackageMapper::toDTO).collect(Collectors.toList());
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}
}
