package mentortools.trainingclass;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrUpdateSyllabusCommand {

    @NotNull
    @Schema(description = "id of the syllabus", example = "1")
    private Long syllabusId;
}
