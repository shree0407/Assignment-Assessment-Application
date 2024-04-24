package dev.shreeya.assignmentAssessment.student;

import dev.shreeya.assignmentAssessment.assignment.Assignments;
import dev.shreeya.assignmentAssessment.assignment.AssignmentsRepository;
import dev.shreeya.assignmentAssessment.exception.UnauthorizedAccessException;
import dev.shreeya.assignmentAssessment.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/student")
@CrossOrigin("*")
public class StudentController {


    private final StudentService studentService;
    private final AssignmentsRepository assignmentsRepository;

    public StudentController(@Qualifier("StudentService") StudentService studentService, AssignmentsRepository assignmentsRepository) {
        this.studentService = studentService;
        this.assignmentsRepository = assignmentsRepository;
    }

    @GetMapping("/assignments")
    public ResponseEntity<?> getAssignmentsByUuid(@RequestParam UUID uuid) {
        List<Assignments> assignments = assignmentsRepository.findByUuid(uuid);
        if (assignments.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Assignment with UUID " + uuid + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        }
    }

    @GetMapping("/download/{uuid}/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable UUID uuid, @PathVariable String fileName) {
        byte[] data = studentService.downloadFile(uuid, fileName);
        ByteArrayResource resource = new ByteArrayResource(data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(resource);
    }


    @PostMapping("student/login")
    public ResponseEntity<?> loginStudent(@RequestBody StudentLoginRequest studentLoginRequest) {
        try {
            Student student=studentService.studentLogin(studentLoginRequest);
            return ResponseEntity.ok(student);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the login request");
        }
    }


}





