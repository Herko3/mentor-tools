package mentortools.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public Student(String name, String email, String githubUserName, String details) {
        this.name = name;
        this.email = email;
        this.githubUserName = githubUserName;
        this.details = details;
    }

    public void update(String name,String email, String github, String detail){
        if(notEmpty(name)){
            setName(name);
        }
        if(notEmpty(email)){
            setEmail(email);
        }
        if(github != null){
            setGithubUserName(github);
        }
        if(detail != null){
            setDetails(detail);
        }
    }

    private boolean notEmpty(String s){
        return s != null && !s.isBlank();
    }
}
