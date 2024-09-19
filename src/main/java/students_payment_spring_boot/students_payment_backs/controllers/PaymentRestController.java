package students_payment_spring_boot.students_payment_backs.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import students_payment_spring_boot.students_payment_backs.entities.Payment;
import students_payment_spring_boot.students_payment_backs.entities.Student;
import students_payment_spring_boot.students_payment_backs.enums.PaymentStatus;
import students_payment_spring_boot.students_payment_backs.enums.PaymentType;
import students_payment_spring_boot.students_payment_backs.repository.PaymentsRepository;
import students_payment_spring_boot.students_payment_backs.repository.StudentsRepository;
import students_payment_spring_boot.students_payment_backs.services.PaymentService;

@RestController
@CrossOrigin("*")
public class PaymentRestController {

    private final PaymentsRepository paymentsRepository;
    private final StudentsRepository studentsRepository;
    private final PaymentService paymentService;

    // Constructor Injection (No need for @Autowired)
    public PaymentRestController(
            PaymentsRepository paymentsRepository,
            StudentsRepository studentsRepository,
            PaymentService paymentService) {
        this.paymentsRepository = paymentsRepository;
        this.studentsRepository = studentsRepository;
        this.paymentService = paymentService;

    }

    // List All Payments
    @GetMapping("/payments")
    public List<Payment> getAllPayments() {
        return paymentsRepository.findAll();
    }

    // Find Payment By Id
    @GetMapping("/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentsRepository.findById(id).orElse(null);
    }

    // List Payments By Students
    @GetMapping("/students/{code}/payments")
    public List<Payment> getAllPaymentsByStudent(@PathVariable String code) {
        return paymentsRepository.findByStudentCode(code);
    }

    // List Payments By Status
    @GetMapping("/payments/byStatus")
    public List<Payment> getAllPaymentsByStatus(@RequestParam PaymentStatus status) {
        return paymentsRepository.findByStatus(status);
    }

    // List Payments By Type
    @GetMapping("/payments/byType")
    public List<Payment> getAllPaymentsByType(@RequestParam PaymentType type) {
        return paymentsRepository.findByType(type);
    }

    // List All Students
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentsRepository.findAll();
    }

    // Find Student By Code
    @GetMapping("/students/{code}")
    public Student getStudentByCode(@PathVariable String code) {
        return studentsRepository.findByCode(code);
    }

    // List All Students By ProgramId
    @GetMapping("/studentsByProgramId")
    public List<Student> getStudentsByProgramId(@RequestParam String programId) {
        return studentsRepository.findByProgramId(programId);
    }

    // Update Payment Status
    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@PathVariable Long id, @RequestParam PaymentStatus status) {
        return this.paymentService.updatePaymentStatus(id, status);
    }

    // Save Payment
    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(
            @RequestParam("file") MultipartFile file,
            @RequestParam("amount") double amount,
            @RequestParam("type") PaymentType type,
            @RequestParam("date") LocalDate date,
            @RequestParam("studentCode") String studentCode) throws IOException {
        return paymentService.savePayment(file, amount, type, date, studentCode);
    }

    // Get Payment File
    @GetMapping(path = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        return this.paymentService.getPaymentFile(paymentId);
    }
}
