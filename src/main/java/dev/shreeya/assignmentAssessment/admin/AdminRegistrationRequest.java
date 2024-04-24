package dev.shreeya.assignmentAssessment.admin;

public record AdminRegistrationRequest(
        String adminName,
        String adminEmail,
        String adminPassword
) {
}
