package dev.shreeya.assignmentAssessment.admin;

import dev.shreeya.assignmentAssessment.exception.DuplicateResourceException;
import dev.shreeya.assignmentAssessment.exception.UnauthorizedAccessException;
import dev.shreeya.assignmentAssessment.exception.UserNotFoundException;
import dev.shreeya.assignmentAssessment.student.StudentRegistrationRequest;
import dev.shreeya.assignmentAssessment.student.StudentUpdateRequest;
import dev.shreeya.assignmentAssessment.teacher.TeacherRegistrationRequest;
import dev.shreeya.assignmentAssessment.teacher.TeacherUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/main")
@CrossOrigin("*")
public class AdminController {
    private final AdminService adminService ;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }




    @PostMapping("teacher/registerTeacher")
    public ResponseEntity<String> registerTeacher(@RequestBody TeacherRegistrationRequest request) {
        try {
            adminService.addTeacher(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Teacher registered successfully");
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already taken");
        }
    }

    @DeleteMapping("teacher/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("teacherId") Long teacherId) {
        adminService.deleteTeacherById(teacherId);
        return ResponseEntity.ok("Teacher deleted successfully");
    }
    @PutMapping("teacher/{teacherId}")
    public ResponseEntity<String> updateTeacher(@PathVariable("teacherId") Long teacherId, @RequestBody TeacherUpdateRequest updateRequest) {
        adminService.updateTeacher(teacherId, updateRequest);
        return ResponseEntity.ok("Teacher updated successfully");
    }



    @PostMapping("student/registerStudent")
    public ResponseEntity<String> registerStudent(@RequestBody StudentRegistrationRequest studentRegistrationRequest) {
        try {
            adminService.addStudent(studentRegistrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Student registered successfully");
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email or PRN is already taken");
        }
    }

    @DeleteMapping("student/{PRN}")
    public ResponseEntity<String> deleteStudent(@PathVariable("PRN") Long studentPRN) {
        adminService.deleteStudentByPRN(studentPRN);
        return ResponseEntity.ok("Student deleted successfully");
    }
    @PutMapping("student/{PRN}")
    public ResponseEntity<String> updateStudent(@PathVariable("PRN") Long studentPRN, @RequestBody StudentUpdateRequest updateRequest) {
        adminService.updateStudent(studentPRN, updateRequest);
        return ResponseEntity.ok("Student updated successfully");
    }

    @PostMapping("admin/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginRequest adminLoginRequest) {
        try {
            Admin admin=adminService.adminLogin(adminLoginRequest);
            return ResponseEntity.ok(admin);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the login request");
        }
    }

    @PostMapping("admin/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminRegistrationRequest request) {
        try {
            adminService.addAdmin(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully");
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already taken");
        }
    }

    @PutMapping("admin/{id}")
    public ResponseEntity<String> updateAdmin(@PathVariable("id") Long adminId, @RequestBody AdminUpdateRequest updateRequest) {
        adminService.updateAdmin(adminId, updateRequest);
        return ResponseEntity.ok("Admin updated successfully");
    }

}
