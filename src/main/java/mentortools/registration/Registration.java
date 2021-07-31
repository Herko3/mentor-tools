package mentortools.registration;

import lombok.*;
import mentortools.students.Student;
import mentortools.trainingclass.TrainingClass;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private TrainingClass trainingClass;

    public Registration(RegistrationStatus status) {
        this.status = status;
    }
}
