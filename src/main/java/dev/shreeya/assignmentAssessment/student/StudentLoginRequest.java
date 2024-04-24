package dev.shreeya.assignmentAssessment.student;

public record StudentLoginRequest(
        Long studentPRN,
        String studentPassword
) {
}
