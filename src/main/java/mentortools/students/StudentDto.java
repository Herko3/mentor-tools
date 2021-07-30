package mentortools.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {

    private Long id;

    private String name;

    private String email;

    private String githubUserName;

    private String details;
}
