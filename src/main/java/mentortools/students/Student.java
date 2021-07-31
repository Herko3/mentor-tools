package mentortools.students;

import lombok.*;
import mentortools.registration.Registration;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(name = "github")
    private String githubUserName;

    private String details;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, mappedBy = "student")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Registration> registrations;

    public Student(String name, String email, String githubUserName, String details) {
        this.name = name;
        this.email = email;
        this.githubUserName = githubUserName;
        this.details = details;
    }

    public void update(String name, String email, String github, String detail) {
        if (notEmpty(name)) {
            setName(name);
        }
        if (notEmpty(email)) {
            setEmail(email);
        }
        if (github != null) {
            setGithubUserName(github);
        }
        if (detail != null) {
            setDetails(detail);
        }
    }

    private boolean notEmpty(String s) {
        return s != null && !s.isBlank();
    }

    public void addRegistration(Registration registration) {
        if (registrations == null) {
            registrations = new HashSet<>();
        }
        registrations.add(registration);
        registration.setStudent(this);
    }
}
