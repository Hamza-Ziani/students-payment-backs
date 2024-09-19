package students_payment_spring_boot.students_payment_backs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String code;

    private String programId;

    private String photo;

    // Bi-directional relationship with Payment (one student can have multiple payments)
    // @OneToMany(mappedBy = "student")
    // private List<Payment> payments;

}
