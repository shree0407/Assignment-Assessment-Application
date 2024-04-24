package dev.shreeya;

import dev.shreeya.assignmentAssessment.student.Student;
import dev.shreeya.assignmentAssessment.teacher.Teacher;
import dev.shreeya.assignmentAssessment.teacher.TeacherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
   public CommandLineRunner test(TeacherService teacherService) {
        return args -> {
            Teacher teacher=new Teacher();
                    teacher.setTeacherName("Anil Surve");
                    teacher.setTeacherBranch("CSE");
                    teacher.setTeacherEmail("anil.surve@walchandsangli.ac.in");
                    teacher.setTeacherPassword("anil@123");
                    teacher.setTeacherSubject("Software Engineering");

            Student student=new Student()  ;
                    student.setStudentPRN(21510101L);
                    student.setStudentName("Shreeya");
                    student.setStudentBranch("CSE");
                    student.setStudentEmail("shreeya.desai@walchandsangli.ac.in");
                    student.setStudentPassword("shreeya@123");
                    student.setStudentYear("Third Year");


        };

    }

}
