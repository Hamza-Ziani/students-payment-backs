package students_payment_spring_boot.students_payment_backs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import students_payment_spring_boot.students_payment_backs.entities.Student;

public interface StudentsRepository extends JpaRepository<Student, String> {

    Student findByCode(String code);

    List<Student> findByProgramId(String programId);

}
