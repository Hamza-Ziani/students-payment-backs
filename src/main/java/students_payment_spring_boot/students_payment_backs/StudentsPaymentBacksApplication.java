package students_payment_spring_boot.students_payment_backs;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import students_payment_spring_boot.students_payment_backs.entities.Payment;
import students_payment_spring_boot.students_payment_backs.entities.Student;
import students_payment_spring_boot.students_payment_backs.enums.PaymentStatus;
import students_payment_spring_boot.students_payment_backs.enums.PaymentType;
import students_payment_spring_boot.students_payment_backs.repository.PaymentsRepository;
import students_payment_spring_boot.students_payment_backs.repository.StudentsRepository;

@SpringBootApplication
public class StudentsPaymentBacksApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentsPaymentBacksApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentsRepository studentsRepository,
			PaymentsRepository paymentsRepository) {
		return args -> {
			// Students
			studentsRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Hamza")
					.lastName("Ziani")
					.code("112233")
					.programId("TDI")
					.photo(null)
					.build());
			studentsRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Jihane")
					.lastName("Chine")
					.code("112244")
					.programId("TDI")
					.photo(null)
					.build());

			studentsRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Ali")
					.lastName("Matan")
					.code("112255")
					.programId("TRI")
					.photo(null)
					.build());

					PaymentType[] paymentTypes = PaymentType.values();
					Random random = new Random();

					studentsRepository.findAll().forEach(student -> {
						// Payments
						for (int i = 0; i < 10; i++) {
							int index = random.nextInt(paymentTypes.length);
							Payment payment = Payment.builder()
							        .amount(1000+(int)(Math.random()*2000))
									.type(paymentTypes[index])
									.status(PaymentStatus.CREATED)
									.date(LocalDate.now())
									.student(student)
									.build();
							paymentsRepository.save(payment);		
						
						}
						
						
					});
		};

	}

}
