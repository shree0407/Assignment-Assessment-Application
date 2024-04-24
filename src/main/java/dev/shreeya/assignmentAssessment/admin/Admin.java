package dev.shreeya.assignmentAssessment.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "admin",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "admin_unique_email",
                        columnNames = "adminEmail"
                )
        }
)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_id_seq")
    @SequenceGenerator(name = "admin_id_seq", sequenceName = "admin_id_seq", allocationSize = 1)
    private Long adminId;

    @Column(
            nullable = false
    )
    private String adminName;

    @Column(
            nullable = false
    )
    private String adminEmail;

    @Column(
            nullable = false
    )
    @JsonIgnore
    private String adminPassword;

    public Admin(Long adminId, String adminName, String adminEmail, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    public Admin() {

    }

    public Admin(String adminName, String adminEmail, String adminPassword) {
        this.adminEmail=adminEmail;
        this.adminName=adminName;
        this.adminPassword=adminPassword;

    }

    public Long getAdminId() {

        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(adminId, admin.adminId) && Objects.equals(adminName, admin.adminName) && Objects.equals(adminEmail, admin.adminEmail) && Objects.equals(adminPassword, admin.adminPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, adminName, adminEmail, adminPassword);
    }
}
