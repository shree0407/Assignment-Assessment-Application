package dev.shreeya.assignmentAssessment.student;

public record StudentUpdateRequest(
        Long studentPRN,
        String studentName,
        String studentEmail,
        String studentPassword,
        String studentBranch,
        String studentYear
) {
}
