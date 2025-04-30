package com.codetest.bookingsystem.repository;

import com.codetest.bookingsystem.enums.UserPackageStatus;
import com.codetest.bookingsystem.model.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {

    Optional<List<UserPackage>> findByUserId(Long userId);

    @Query("SELECT u FROM UserPackage u WHERE u.userId = :userId AND u.userPackageStatus = :userPackageStatus")
    UserPackage findByUserIdAndStatus(@Param("userId") Long userId, @Param("userPackageStatus") UserPackageStatus status);

    @Query("SELECT u FROM UserPackage u WHERE u.userId = :userId AND u.userPackageStatus = :userPackageStatus")
    Optional<UserPackage> findByIdWithStatus(@Param("userId") Long userId, @Param("userPackageStatus") UserPackageStatus status);


}
