package com.codetest.bookingsystem.repository;

import com.codetest.bookingsystem.enums.Role;
import com.codetest.bookingsystem.enums.Status;
import com.codetest.bookingsystem.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT e FROM User e WHERE e.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT e FROM User e WHERE e.role = :role and e.status= :status")
    User findByRole(@Param("role") Role role, @Param("status") Status status);
}
