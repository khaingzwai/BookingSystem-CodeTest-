package com.codetest.bookingsystem.repository;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
