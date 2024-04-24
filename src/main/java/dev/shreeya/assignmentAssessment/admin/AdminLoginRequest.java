package dev.shreeya.assignmentAssessment.admin;

public record AdminLoginRequest(
        String adminEmail,
        String adminPassword
) {
}
