package dev.shreeya.assignmentAssessment.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_unique_email",
                        columnNames = "studentEmail"
                )
        }
)
public class Student {

    @Id
    Long studentPRN;

    @Column(
            nullable = false
    )
    String studentName;

    @Column(
            nullable = false
    )
    String studentEmail;

    @JsonIgnore
    @Column(
            nullable = false
    )
    String studentPassword;

    @Column(
            nullable = false
    )
    String studentBranch;

    @Column(
            nullable = false
    )
    String studentYear;

    public Student(Long PRN, String studentName, String email, String studentPassword, String branch, String studentYear) {
        this.studentPRN = PRN;
        this.studentName = studentName;
        this.studentEmail = email;
        this.studentPassword = studentPassword;
        this.studentBranch = branch;
        this.studentYear = studentYear;
    }

    public Student() {

    }

    public Student(String studentName, String email, String studentPassword, String branch, String studentYear) {
        this.studentName = studentName;
        this.studentEmail = email;
        this.studentPassword = studentPassword;
        this.studentBranch = branch;
        this.studentYear = studentYear;
    }

    public Student(Long studentPRN, String studentName, String studentEmail, String studentBranch, String studentYear) {
        this.studentPRN = studentPRN;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentBranch = studentBranch;
        this.studentYear = studentYear;
    }

    public Long getStudentPRN() {
        return studentPRN;
    }

    public void setStudentPRN(Long PRN) {
        this.studentPRN = PRN;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String name) {
        this.studentName = name;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String email) {
        this.studentEmail = email;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String password) {
        this.studentPassword = password;
    }

    public String getStudentBranch() {
        return studentBranch;
    }

    public void setStudentBranch(String branch) {
        this.studentBranch = branch;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public void setStudentYear(String year) {
        this.studentYear = year;
    }

    @Override
    public String toString() {
        return "Student{" +
                "PRN=" + studentPRN +
                ", name='" + studentName + '\'' +
                ", email='" + studentEmail + '\'' +
                ", password='" + studentPassword + '\'' +
                ", branch='" + studentBranch + '\'' +
                ", year='" + studentYear + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentPRN, student.studentPRN) && Objects.equals(studentName, student.studentName) && Objects.equals(studentEmail, student.studentEmail) && Objects.equals(studentPassword, student.studentPassword) && Objects.equals(studentBranch, student.studentBranch) && Objects.equals(studentYear, student.studentYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentPRN, studentName, studentEmail, studentPassword, studentBranch, studentYear);
    }
}
