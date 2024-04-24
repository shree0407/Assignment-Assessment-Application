package dev.shreeya.assignmentAssessment.assignment;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;


@Entity
@Table(
        name = "assignments"
)
public class Assignments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignments_id_seq")
    @SequenceGenerator(name = "assignments_id_seq", sequenceName = "assignments_id_seq", allocationSize = 1)
    Long id;

    @Column(
            nullable = false
    )
    UUID uuid;

    @Column(
            nullable = false
    )
    String fileName;


    public Assignments() {

    }

    public Assignments(UUID uuid, String fileName,Long id) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.id=id;
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Assignments{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignments that = (Assignments) o;
        return Objects.equals(id, that.id) && Objects.equals(uuid, that.uuid) && Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, fileName);
    }
}
