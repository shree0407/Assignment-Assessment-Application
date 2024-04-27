package dev.shreeya.assignmentAssessment.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("TeacherRepository")
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsTeacherByTeacherEmail(String teacherEmail);

    boolean existsTeacherByTeacherId(Long teacherId);

    Optional<Teacher> getTeacherByTeacherId(Long teacherId);

    Optional<Teacher> findByTeacherEmail(String teacherEmail);

    void deleteTeacherByTeacherId(Long teacherId);

    List<Teacher> findAllByTeacherBranch(String teacherBranch);
}
