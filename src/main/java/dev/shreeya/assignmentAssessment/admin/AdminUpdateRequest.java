package dev.shreeya.assignmentAssessment.admin;

public record AdminUpdateRequest(
        String adminName,
        String adminEmail,
        String adminPassword
) {
}
