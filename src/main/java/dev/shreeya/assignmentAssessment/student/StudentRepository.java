package dev.shreeya.assignmentAssessment.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("StudentRepository")
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsStudentByStudentEmail(String studentEmail);

    boolean existsStudentByStudentPRN(Long PRN);

    Optional<Student> getStudentByStudentPRN(Long PRN);

    Optional<Student> findStudentByStudentEmail (String studentEmail);

    void deleteStudentByStudentPRN(Long PRN);
}
