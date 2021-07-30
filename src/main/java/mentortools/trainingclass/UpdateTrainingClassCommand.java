package mentortools.trainingclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@After
public class UpdateTrainingClassCommand implements TrainingClassCommand{

    @JsonProperty
    @Schema(description = "name of the course, not required", example = "Struktúra Váltás")
    private String name;

    @JsonProperty
    @Schema(description = "the start date of the course, not required", example = "2020-10-10T08:08:00")
    private LocalDateTime startTime;

    @JsonProperty
    @Schema(description = "the start date of the course, not required", example = "2021-10-10T08:08:00")
    private LocalDateTime endTime;
}
