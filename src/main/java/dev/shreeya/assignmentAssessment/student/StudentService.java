package dev.shreeya.assignmentAssessment.student;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import dev.shreeya.assignmentAssessment.assignment.Assignments;
import dev.shreeya.assignmentAssessment.assignment.AssignmentsRepository;
import dev.shreeya.assignmentAssessment.exception.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("StudentService")
public class StudentService {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;
    private final AssignmentsRepository assignmentsRepository;
    private final StudentRepository studentRepository;

    public StudentService(AmazonS3 s3Client, AssignmentsRepository assignmentsRepository, @Qualifier("StudentRepository") StudentRepository studentRepository) {
        this.s3Client = s3Client;
        this.assignmentsRepository = assignmentsRepository;
        this.studentRepository = studentRepository;
    }


    public List<Assignments> getAssignmentsByUuid(UUID uuid) {

        return assignmentsRepository.findByUuid(uuid);
    }


    public byte[] downloadFile(UUID uuid,String fileName) {
        String objectKey = uuid.toString() + "_" + fileName;


        S3Object s3Object = s3Client.getObject(bucketName, objectKey);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public Student studentLogin(StudentLoginRequest studentLoginRequest) {
        Long PRN=studentLoginRequest.studentPRN();
        Optional<Student> optionalStudent=studentRepository.getStudentByStudentPRN(PRN);
        if(optionalStudent.isEmpty()) {
            throw new UserNotFoundException("Student with PRN " + PRN + " not found");
        }

        Student student=optionalStudent.get();
        if(studentLoginRequest.studentPassword().equals(student.getStudentPassword())) {
            return new Student(
                    student.getStudentPRN(),
                    student.getStudentName(),
                    student.getStudentEmail(),
                    student.getStudentBranch(),
                    student.getStudentYear()
            );
        } else {
            throw new UnauthorizedAccessException("Invalid PRN or password");
        }
    }


}
