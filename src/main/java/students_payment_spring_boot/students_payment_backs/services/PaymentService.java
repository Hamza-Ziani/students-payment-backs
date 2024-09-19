package students_payment_spring_boot.students_payment_backs.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import students_payment_spring_boot.students_payment_backs.entities.Payment;
import students_payment_spring_boot.students_payment_backs.enums.PaymentStatus;
import students_payment_spring_boot.students_payment_backs.enums.PaymentType;
import students_payment_spring_boot.students_payment_backs.repository.PaymentsRepository;
import students_payment_spring_boot.students_payment_backs.repository.StudentsRepository;

@Service
public class PaymentService {

    private PaymentsRepository paymentsRepository;
    private StudentsRepository studentsRepository;

    public PaymentService(PaymentsRepository paymentsRepository, StudentsRepository studentsRepository) {
        this.paymentsRepository = paymentsRepository;
        this.studentsRepository = studentsRepository;
    }

    // Save Payment :
    public Payment savePayment(
            MultipartFile file,
            double amount,
            PaymentType type,
            LocalDate date, // Receive date as a String
            String studentCode) throws IOException {

        // Path to save uploaded files
        Path folderPath = Paths.get(System.getProperty("user.home"), "Downloads", "payments");

        // Create directory if it doesn't exist
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (Exception e) {
                throw new IOException("Could not create directory to save file.", e);
            }
        }

        // Create unique filename
        String fileName = UUID.randomUUID().toString() + ".pdf";
        Path filePath = folderPath.resolve(fileName);

        // Save file to disk
        Files.copy(file.getInputStream(), filePath);

        // Build and save payment
        Payment payment = Payment.builder()
                .date(date) // Use the parsed date
                .amount(amount) // Use the amount parameter
                .type(type) // Use the type parameter
                .status(PaymentStatus.CREATED)
                .file(filePath.toUri().toString()) // Save the file path
                .student(studentsRepository.findByCode(studentCode)) // Find student by code
                .build();

        return paymentsRepository.save(payment);
    }

    // Get Payment File :
    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentsRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }

    // Update Payment Status :
    public Payment updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = paymentsRepository.findById(id).get();
        payment.setStatus(status);
        return paymentsRepository.save(payment);
    }

}
