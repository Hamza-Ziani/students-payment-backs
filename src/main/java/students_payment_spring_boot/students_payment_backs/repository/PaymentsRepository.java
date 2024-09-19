package students_payment_spring_boot.students_payment_backs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import students_payment_spring_boot.students_payment_backs.entities.Payment;
import students_payment_spring_boot.students_payment_backs.enums.PaymentStatus;
import students_payment_spring_boot.students_payment_backs.enums.PaymentType;

public interface PaymentsRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentCode(String Code);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByType(PaymentType type);



}
