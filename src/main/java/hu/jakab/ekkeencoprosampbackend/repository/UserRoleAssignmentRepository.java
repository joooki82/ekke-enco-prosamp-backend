package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.UserRoleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRoleAssignmentRepository extends JpaRepository<UserRoleAssignment, Long> {
    List<UserRoleAssignment> findByUserId(UUID userId);
}
