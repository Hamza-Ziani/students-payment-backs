package students_payment_spring_boot.students_payment_backs.dtos;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import students_payment_spring_boot.students_payment_backs.enums.PaymentType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPaymentDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    
    private double amount;

    
    private PaymentType type;

    
    private String studentCode;
}
