package mentortools.syllabuses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSyllabusCommand {

    @NotBlank(message = "Name must not be empty")
    @Size(max = 255)
    @Schema(description = "the name of the Syllabus", example = "JPA")
    private String name;
}
