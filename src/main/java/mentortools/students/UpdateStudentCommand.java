package mentortools.students;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentCommand {

    @JsonProperty
    @Size(max = 255)
    @Schema(description = "name of the student, not required", example = "John Doe")
    private String name;

    @JsonProperty
    @Size(max = 255)
    @Schema(description = "email of the student, not required", example = "example@example.org")
    private String email;

    @JsonProperty
    @Size(max = 255)
    @Schema(description = "github user name of the student, not required", example = "Johnny_Boi325")
    private String githubUserName;

    @JsonProperty
    @Size(max = 255)
    @Schema(description = "any other useful information about the student", example = "also working")
    private String details;
}
