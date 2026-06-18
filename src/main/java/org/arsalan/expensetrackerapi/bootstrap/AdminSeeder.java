package org.arsalan.expensetrackerapi.bootstrap;

import org.arsalan.expensetrackerapi.auth.entity.User;
import org.arsalan.expensetrackerapi.auth.repository.UserRepository;
import org.arsalan.expensetrackerapi.common.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {
    @Value("${admin.fullName}")
    private String adminFullName;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByEmail(adminEmail).isEmpty()){
            User admin = new User();
            admin.setFullName(adminFullName);
            admin.setEmail(adminEmail);
            admin.setPassword(
                    passwordEncoder.encode(adminPassword)
            );

            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }

    }
}
