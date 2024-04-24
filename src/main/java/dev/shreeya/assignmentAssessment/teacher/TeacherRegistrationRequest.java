package dev.shreeya.assignmentAssessment.teacher;

public record TeacherRegistrationRequest (
    String teacherName,
    String teacherEmail,
    String teacherPassword,
    String teacherBranch,
    String teacherSubject
)
{}