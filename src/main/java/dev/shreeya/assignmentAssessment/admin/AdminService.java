package dev.shreeya.assignmentAssessment.admin;

import dev.shreeya.assignmentAssessment.exception.*;
import dev.shreeya.assignmentAssessment.student.Student;
import dev.shreeya.assignmentAssessment.student.StudentRegistrationRequest;
import dev.shreeya.assignmentAssessment.student.StudentRepository;
import dev.shreeya.assignmentAssessment.student.StudentUpdateRequest;
import dev.shreeya.assignmentAssessment.teacher.Teacher;
import dev.shreeya.assignmentAssessment.teacher.TeacherRegistrationRequest;
import dev.shreeya.assignmentAssessment.teacher.TeacherRepository;
import dev.shreeya.assignmentAssessment.teacher.TeacherUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("AdminService")
public class AdminService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;


    public AdminService(TeacherRepository teacherRepository, StudentRepository studentRepository, AdminRepository adminRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
    }



    public Teacher getTeacher(Long id) {
        return teacherRepository.getTeacherByTeacherId(id).orElseThrow(() ->
                new ResourceNotFoundException("Teacher with id [%s] not found".formatted(id)));

    }

    public List<Map<String, String>> getAllTeachersByBranch( String teacherBranch) {
        List<Teacher> teachers = teacherRepository.findAllByTeacherBranch( teacherBranch);
        List<Map<String, String>> teacherDetails = new ArrayList<>();

        for (Teacher teacher : teachers) {
            Map<String, String> teacherInfo = new HashMap<>();
            teacherInfo.put("name", teacher.getTeacherName());
            teacherDetails.add(teacherInfo);
        }

        return teacherDetails;
    }



    public void addTeacher(TeacherRegistrationRequest teacherRegistrationRequest) {
        String email = teacherRegistrationRequest.teacherEmail();
        if (teacherRepository.existsTeacherByTeacherEmail(email)) {
            throw new DuplicateResourceException("Email already taken");
        }

        Teacher teacher = new Teacher(
                teacherRegistrationRequest.teacherName(),
                teacherRegistrationRequest.teacherEmail(),
                teacherRegistrationRequest.teacherPassword(),
                teacherRegistrationRequest.teacherBranch(),
                teacherRegistrationRequest.teacherSubject()
        );
        teacherRepository.save(teacher);
    }

    @Transactional
    public void deleteTeacherById(Long teacherId) {
        if (!teacherRepository.existsTeacherByTeacherId(teacherId)) {
            throw new ResourceNotFoundException("Customer with id [%s] not found".formatted(teacherId));

        }

        teacherRepository.deleteTeacherByTeacherId(teacherId);
    }


    public void updateTeacher(Long teacherId, TeacherUpdateRequest teacherUpdateRequest) {
        Teacher teacher = getTeacher(teacherId);
        boolean changes = false;

        if (teacherUpdateRequest.teacherName() != null && !teacherUpdateRequest.teacherName().equals(teacher.getTeacherName())) {
            teacher.setTeacherName(teacherUpdateRequest.teacherName());
            changes = true;
        }

        if (teacherUpdateRequest.teacherEmail() != null && !teacherUpdateRequest.teacherEmail().equals(teacher.getTeacherEmail())) {
            if (teacherRepository.existsTeacherByTeacherEmail(teacherUpdateRequest.teacherEmail())) {
                throw new DuplicateResourceException("Email is already taken");
            }
            teacher.setTeacherEmail(teacherUpdateRequest.teacherEmail());
            changes = true;
        }

        if (teacherUpdateRequest.teacherPassword() != null && !teacherUpdateRequest.teacherPassword().equals(teacher.getTeacherPassword())) {
            teacher.setTeacherPassword(teacherUpdateRequest.teacherPassword());
            changes = true;
        }

        if (teacherUpdateRequest.teacherBranch() != null && !teacherUpdateRequest.teacherBranch().equals(teacher.getTeacherBranch())) {
            teacher.setTeacherBranch(teacherUpdateRequest.teacherBranch());
            changes = true;
        }

        if (teacherUpdateRequest.teacherSubject() != null && !teacherUpdateRequest.teacherSubject().equals(teacher.getTeacherSubject())) {
            teacher.setTeacherSubject(teacherUpdateRequest.teacherSubject());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes found");
        }

        teacherRepository.save(teacher);
    }


    public Student getStudent(Long PRN) {
        return studentRepository.findById(PRN).orElseThrow(() ->
                new ResourceNotFoundException("Student with PRN [%s] not found".formatted(PRN)));
    }

    public List<Map<String, Object>> getAllStudents(Optional<Long> PRN, Optional<String> studentName, String studentBranch) {
        List<Student> students;

        if (PRN.isPresent() && studentName.isPresent()) {
            students = studentRepository.findAllByStudentBranchAndStudentPRNAndStudentName(studentBranch, PRN.get(), studentName.get());
        } else if (PRN.isPresent()) {
            students = studentRepository.findAllByStudentBranchAndStudentPRN(studentBranch, PRN.get());
        } else if (studentName.isPresent()) {
            students = studentRepository.findAllByStudentBranchAndStudentName(studentBranch, studentName.get());
        } else {
            students = studentRepository.findAllByStudentBranch(studentBranch);
        }

        List<Map<String, Object>> studentDetails = new ArrayList<>();

        for (Student student : students) {
            Map<String, Object> studentInfo = new HashMap<>();
            studentInfo.put("name", student.getStudentName());
            studentInfo.put("PRN", student.getStudentPRN());
            studentDetails.add(studentInfo);
        }

        return studentDetails;
    }

    public void addStudent(StudentRegistrationRequest studentRegistrationRequest) {
        String email=studentRegistrationRequest.studentEmail();
        if(studentRepository.existsStudentByStudentEmail(email)) {
            throw new DuplicateResourceException("Email already taken");
        }

        Student student = new Student(
                studentRegistrationRequest.studentPRN(),
                studentRegistrationRequest.studentName(),
                studentRegistrationRequest.studentEmail(),
                studentRegistrationRequest.studentPassword(),
                studentRegistrationRequest.studentBranch(),
                studentRegistrationRequest.studentYear()

        );
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudentByPRN(Long PRN) {
        if(!studentRepository.existsStudentByStudentPRN(PRN)) {
            throw new ResourceNotFoundException("Student with PRN [%s] not found".formatted(PRN));
        }

        studentRepository.deleteStudentByStudentPRN(PRN);
    }

    public void updateStudent(Long PRN, StudentUpdateRequest studentUpdateRequest) {
        Student student=getStudent(PRN);
        boolean changes=false;


        if(studentUpdateRequest.studentPRN()!= null && !studentUpdateRequest.studentPRN().equals(student.getStudentPRN())) {
            student.setStudentPRN(studentUpdateRequest.studentPRN());
            changes=true;
        }

        if(studentUpdateRequest.studentName()!= null && !studentUpdateRequest.studentName().equals(student.getStudentName())) {
            student.setStudentName(studentUpdateRequest.studentName());
            changes=true;
        }

        if(studentUpdateRequest.studentEmail()!= null && !studentUpdateRequest.studentEmail().equals(student.getStudentEmail())) {
            if(studentRepository.existsStudentByStudentEmail(studentUpdateRequest.studentEmail())) {
                throw new DuplicateResourceException("Email is already taken");
            }
            student.setStudentEmail(studentUpdateRequest.studentEmail());
            changes=true;
        }

        if(studentUpdateRequest.studentPassword()!= null && !studentUpdateRequest.studentPassword().equals(student.getStudentPassword())) {
            student.setStudentPassword(studentUpdateRequest.studentPassword());
            changes=true;
        }

        if(studentUpdateRequest.studentBranch()!= null && !studentUpdateRequest.studentBranch().equals(student.getStudentBranch())) {
            student.setStudentBranch(studentUpdateRequest.studentBranch());
            changes=true;
        }

        if(studentUpdateRequest.studentYear()!= null && !studentUpdateRequest.studentYear().equals(student.getStudentYear())) {
            student.setStudentYear(studentUpdateRequest.studentYear());
            changes=true;
        }

        if(!changes) {
            throw new RequestValidationException("No data changes found");
        }

        studentRepository.save(student);
    }


    public Admin adminLogin(AdminLoginRequest adminLoginRequest) {
        String email = adminLoginRequest.adminEmail();
        Optional<Admin> optionalAdmin = adminRepository.findByAdminEmail(email);
        if (optionalAdmin.isEmpty()) {
            throw new UserNotFoundException("Teacher with email " + email + " not found");
        }
        Admin admin = optionalAdmin.get();

        if (adminLoginRequest.adminPassword().equals(admin.getAdminPassword())) {
            return new Admin(
                    admin.getAdminId(),
                    admin.getAdminEmail(),
                    admin.getAdminPassword(),
                    admin.getAdminName()
            );
        } else {
            throw new UnauthorizedAccessException("Invalid email or password");
        }
    }

    public Admin getAdmin(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(() ->
                new ResourceNotFoundException("Admin with id [%s] not found".formatted(adminId)));
    }


    public void addAdmin(AdminRegistrationRequest adminRegistrationRequest) {
        String email = adminRegistrationRequest.adminEmail();
        if (adminRepository.existsAdminByAdminEmail(email)) {
            throw new DuplicateResourceException("Email already taken");
        }

        Admin admin=new Admin(
                adminRegistrationRequest.adminName(),
                adminRegistrationRequest.adminEmail(),
                adminRegistrationRequest.adminPassword()
        );
        adminRepository.save(admin);
    }


    public void updateAdmin(Long adminId, AdminUpdateRequest adminUpdateRequest) {
        Admin admin=getAdmin(adminId);
        boolean changes=false;


        if(adminUpdateRequest.adminName()!= null && !adminUpdateRequest.adminName().equals(admin.getAdminName())) {
            admin.setAdminName(adminUpdateRequest.adminName());
            changes=true;
        }


        if(adminUpdateRequest.adminEmail()!= null && !adminUpdateRequest.adminEmail().equals(admin.getAdminEmail())) {
            if(adminRepository.existsAdminByAdminEmail(adminUpdateRequest.adminEmail())) {
                throw new DuplicateResourceException("Email is already taken");
            }
            admin.setAdminEmail(adminUpdateRequest.adminEmail());
            changes=true;
        }

        if(adminUpdateRequest.adminPassword()!= null && !adminUpdateRequest.adminPassword().equals(admin.getAdminPassword())) {
            admin.setAdminPassword(adminUpdateRequest.adminPassword());
            changes=true;
        }


        if(!changes) {
            throw new RequestValidationException("No data changes found");
        }
        adminRepository.save(admin);
    }

}
