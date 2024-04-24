package dev.shreeya.assignmentAssessment.teacher;

public record TeacherUpdateRequest(
        String teacherName,
        String teacherEmail,
        String teacherPassword,
        String teacherBranch,
        String teacherSubject
) {



}
