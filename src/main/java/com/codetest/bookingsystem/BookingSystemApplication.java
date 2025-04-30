package com.codetest.bookingsystem;

import com.codetest.bookingsystem.enums.Role;
import com.codetest.bookingsystem.enums.Status;
import com.codetest.bookingsystem.model.User;
import com.codetest.bookingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.codetest.bookingsystem")
@EnableCaching
@RequiredArgsConstructor
@EnableJpaRepositories
public class BookingSystemApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookingSystemApplication.class, args);
    }

    @Override
    public void run(String... args) {
        User adminAccount = userRepository.findByRole(Role.ADMIN, Status.VALID);
        if (adminAccount == null) {
            User newAccount = new User();
            newAccount.setRole(Role.ADMIN);
            newAccount.setUserName("Admin");
            newAccount.setEmail("admin@account.com");
            newAccount.setStatus(Status.VALID);
            newAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(newAccount);
        }
    }
}
