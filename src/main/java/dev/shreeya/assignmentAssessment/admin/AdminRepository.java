package dev.shreeya.assignmentAssessment.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("AdminRepository")
public interface AdminRepository extends JpaRepository<Admin,Long> {

    Optional<Admin> findByAdminEmail(String adminEmail);

    boolean existsAdminByAdminEmail(String adminEmail);

    Optional<Admin> getAdminByAdminId(Long adminId);




}
