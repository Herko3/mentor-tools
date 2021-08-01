package mentortools.completion;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLessonCompletionCommand {

    @JsonProperty
    @Schema(name = "the id of the lesson, not required", example = "1")
    private Long lessonId;

    @JsonProperty
    @Schema(description = "Status of the video, not required", example = "COMPLETED")
    private CompletionStatus videoStatus;

    @JsonProperty
    @Schema(description = "the date when the video was completed, not required", example = "2020-10-10T08:08:00")
    private LocalDateTime videoDate;

    @JsonProperty
    @Schema(description = "Status of the exercise, not required", example = "COMPLETED")
    private CompletionStatus exerciseStatus;

    @JsonProperty
    @Schema(description = "the date when the exercise was completed, not required", example = "2020-10-10T08:08:00")
    private LocalDateTime exerciseDate;

    @JsonProperty
    @Size(max = 255)
    @Schema(description = "the url of the committed exercise, not required",example = "github.com/")
    private String commitUrl;
}
