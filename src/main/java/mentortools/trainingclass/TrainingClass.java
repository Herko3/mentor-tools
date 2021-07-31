package mentortools.trainingclass;

import lombok.*;
import mentortools.registration.Registration;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "training_classes")
public class TrainingClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "trainingClass")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Registration> registrations;

    public TrainingClass(String name, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void update(String name, LocalDateTime start, LocalDateTime end) {
        if (name != null && !name.isBlank()) {
            setName(name);
        }
        if (start != null) {
            if (end == null && start.isAfter(endTime)) {
                throw new InvalidEndTimeException("End time must be after Start time");
            }
            setStartTime(start);
        }

        if (end != null) {
            if (startTime.isAfter(end)) {
                throw new InvalidEndTimeException("End time must be after Start time");
            }
            setEndTime(end);
        }
    }

    public void addRegistration(Registration registration){
        if(registrations == null){
            registrations = new HashSet<>();
        }
        registrations.add(registration);
        registration.setTrainingClass(this);
    }
}
