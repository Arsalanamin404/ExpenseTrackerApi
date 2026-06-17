package org.arsalan.expensetrackerapi.bootstrap;

import org.arsalan.expensetrackerapi.auth.entity.User;
import org.arsalan.expensetrackerapi.auth.repository.UserRepository;
import org.arsalan.expensetrackerapi.common.enums.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()){
            User admin = new User();
            admin.setFullName("SYSTEM ADMIN");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(
                    passwordEncoder.encode("admin@123")
            );

            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }

    }
}
