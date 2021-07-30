package mentortools.trainingclass;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@After
public class CreateTrainingClassCommand implements TrainingClassCommand {

    @NotBlank(message = "Must not be empty")
    @Size(max = 255)
    @Schema(description = "name of the course", example = "Struktúra Váltás")
    private String name;

    @NotNull
    @Schema(description = "the start date of the course", example = "2020-10-10T08:08:00")
    private LocalDateTime startTime;

    @NotNull
    @Schema(description = "the end date of the course, must be after the start", example = "2020-11-10T08:08:00")
    private LocalDateTime endTime;
}
