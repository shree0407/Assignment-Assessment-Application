package dev.shreeya.assignmentAssessment.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "teacher",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "teacher_unique_email",
                        columnNames = "teacherEmail"
                )
        }
)
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_id_seq")
    @SequenceGenerator(name = "teacher_id_seq", sequenceName = "teacher_id_seq", allocationSize = 1)
    private Long teacherId;

    @Column(
            nullable = false
    )
    private String teacherName;

    @Column(
            nullable = false
    )
    private String teacherEmail;

    @Column(
            nullable = false
    )
    @JsonIgnore
    private String teacherPassword;

    @Column(
            nullable = false
    )
    private String teacherBranch;

    @Column(
            nullable = false
    )
    private String teacherSubject;

    public Teacher(Long teacherId, String teacherName, String teacherEmail, String teacherPassword, String teacherBranch, String teacherSubject) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherPassword = teacherPassword;
        this.teacherBranch = teacherBranch;
        this.teacherSubject = teacherSubject;
    }

    public Teacher() {
    }

    public Teacher(String teacherName, String teacherEmail, String teacherPassword, String teacherBranch, String teacherSubject) {
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherPassword = teacherPassword;
        this.teacherBranch = teacherBranch;
        this.teacherSubject = teacherSubject;
    }

    public Teacher(Long teacherId,String teacherName, String teacherEmail, String teacherBranch, String teacherSubject) {
        this.teacherName=teacherName;
        this.teacherEmail=teacherEmail;
        this.teacherBranch=teacherBranch;
        this.teacherSubject=teacherSubject;
        this.teacherId=teacherId;
    }


    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getTeacherBranch() {
        return teacherBranch;
    }

    public void setTeacherBranch(String teacherBranch) {
        this.teacherBranch = teacherBranch;
    }

    public String getTeacherSubject() {
        return teacherSubject;
    }

    public void setTeacherSubject(String teacherSubject) {
        this.teacherSubject = teacherSubject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", teacherEmail='" + teacherEmail + '\'' +
                ", teacherPassword='" + teacherPassword + '\'' +
                ", teacherBranch='" + teacherBranch + '\'' +
                ", teacherSubject='" + teacherSubject + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Teacher teacher = (Teacher) obj;
        return teacherId.equals(teacher.teacherId) && teacherName.equals(teacher.teacherName) && teacherEmail.equals(teacher.teacherEmail) && teacherPassword.equals(teacher.teacherPassword) && teacherBranch.equals(teacher.teacherBranch) && teacherSubject.equals(teacher.teacherSubject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, teacherName, teacherEmail, teacherPassword, teacherBranch, teacherSubject);
    }


}
