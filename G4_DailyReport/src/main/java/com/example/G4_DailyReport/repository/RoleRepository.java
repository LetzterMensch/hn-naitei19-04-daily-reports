package com.example.G4_DailyReport.repository;
import com.example.G4_DailyReport.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
