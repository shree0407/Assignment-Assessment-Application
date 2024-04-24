package dev.shreeya.assignmentAssessment.teacher;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import dev.shreeya.assignmentAssessment.assignment.Assignments;
import dev.shreeya.assignmentAssessment.assignment.AssignmentsRepository;
import dev.shreeya.assignmentAssessment.exception.UnauthorizedAccessException;
import dev.shreeya.assignmentAssessment.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("TeacherService")
public class TeacherService {
    @Value("${application.bucket.name}")
    private String bucketName;

    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;
    private final AmazonS3 s3Client;
    private final AssignmentsRepository assignmentsRepository;


    public TeacherService(AmazonS3 s3Client, AssignmentsRepository assignmentsRepository, @Qualifier("TeacherRepository") TeacherRepository teacherRepository) {
        this.s3Client = s3Client;
        this.assignmentsRepository = assignmentsRepository;
        this.teacherRepository = teacherRepository;

    }


    public ResponseEntity<byte[]> uploadFiles(List<MultipartFile> files) {

        UUID uuid = UUID.randomUUID();

        for (MultipartFile file : files) {
            File fileObj = convertMultiPartFileToFile(file);
            String originalFileName = file.getOriginalFilename();
            String renameFile = uuid + "_" + originalFileName;

            Assignments assignment = new Assignments();
            assignment.setFileName(originalFileName);
            assignment.setUuid(uuid);
            assignmentsRepository.save(assignment);

            s3Client.putObject(new PutObjectRequest(bucketName, renameFile, fileObj));

        }
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        byte[] qrBytes = qrCodeGenerator.generateQRCodeWithUUID(uuid, 300, 300);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentDispositionFormData("filename", "qr_code.png");

        return new ResponseEntity<>(qrBytes, headers, HttpStatus.OK);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            logger.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        assignmentsRepository.deleteAssignmentsByFileName(fileName);
        return fileName + " removed ...";

    }





    public Teacher teacherLogin(TeacherLoginRequest teacherLoginRequest) {
        String email = teacherLoginRequest.teacherEmail();
        Optional<Teacher> optionalTeacher = teacherRepository.findByTeacherEmail(email);
        if (optionalTeacher.isEmpty()) {
            throw new UserNotFoundException("Teacher with email " + email + " not found");
        }
        Teacher teacher = optionalTeacher.get();

        if (teacherLoginRequest.teacherPassword().equals(teacher.getTeacherPassword())) {
            return new Teacher(
                    teacher.getTeacherId(),
                    teacher.getTeacherName(),
                    teacher.getTeacherEmail(),
                    teacher.getTeacherBranch(),
                    teacher.getTeacherSubject()
            );
        } else {
            throw new UnauthorizedAccessException("Invalid email or password");
        }
    }

}
