package dev.shreeya.assignmentAssessment.teacher;

import dev.shreeya.assignmentAssessment.exception.UnauthorizedAccessException;
import dev.shreeya.assignmentAssessment.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(@Qualifier("TeacherService") TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") List<MultipartFile> files) {
        try {
            ResponseEntity<byte[]> response = teacherService.uploadFiles(files);
            HttpHeaders headers = response.getHeaders();
            return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while uploading files");
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        String message;
        HttpStatus status;

                message = "File deleted successfully";
                status = HttpStatus.OK;



        return ResponseEntity.status(status).body(message);
    }



    @PostMapping("teacher/login")
    public ResponseEntity<?> loginTeacher(@RequestBody TeacherLoginRequest teacherLoginRequest) {
        try {
            Teacher teacher = teacherService.teacherLogin(teacherLoginRequest);
            return ResponseEntity.ok(teacher);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the login request");
        }
    }


}
