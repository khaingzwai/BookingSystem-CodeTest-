package com.codetest.bookingsystem.repository;

import com.codetest.bookingsystem.model.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Packages, Long> {
}
