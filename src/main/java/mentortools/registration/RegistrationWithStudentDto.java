package mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationWithStudentDto {

    private RegistrationStatus status;
    private StudentRegisteredDto student;
}
