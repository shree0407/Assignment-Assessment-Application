package dev.shreeya.assignmentAssessment.student;

public record StudentRegistrationRequest(
        Long studentPRN,
        String studentName,
        String studentEmail,
        String studentPassword,
        String studentBranch,
        String studentYear
) {
}
