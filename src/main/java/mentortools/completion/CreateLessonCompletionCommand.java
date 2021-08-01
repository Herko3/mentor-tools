package mentortools.completion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLessonCompletionCommand {

    @NotNull(message = "lesson id cannot be null")
    @Schema(name = "the id of the lesson", example = "1")
    private Long lessonId;

}
