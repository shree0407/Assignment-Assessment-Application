package dev.shreeya.assignmentAssessment.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentsRepository extends JpaRepository<Assignments, UUID> {

    List<Assignments> findByUuid(UUID uuid);

    Assignments findByUuidAndFileName(UUID uuid, String fileName);

    Assignments deleteAssignmentsByFileName(String fileName);

    boolean existsByFileName(String fileName);
}
