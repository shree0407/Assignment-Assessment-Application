package dev.shreeya.assignmentAssessment.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("StudentRepository")
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsStudentByStudentEmail(String studentEmail);

    boolean existsStudentByStudentPRN(Long PRN);

    Optional<Student> getStudentByStudentPRN(Long PRN);

    Optional<Student> findStudentByStudentEmail (String studentEmail);

    void deleteStudentByStudentPRN(Long PRN);

    List<Student> findAllByStudentBranch(String studentBranch);
    List<Student> findAllByStudentBranchAndStudentPRN(String studentBranch, Long studentPRN);
    List<Student> findAllByStudentBranchAndStudentName(String studentBranch, String studentName);
    List<Student> findAllByStudentBranchAndStudentPRNAndStudentName(String studentBranch, Long studentPRN, String studentName);
}
