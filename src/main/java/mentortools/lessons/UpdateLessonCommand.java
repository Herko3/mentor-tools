package mentortools.lessons;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull
public class UpdateLessonCommand {

    @JsonProperty
    @Size(max = 255)
    @Schema(description = "the name of the lesson, not required", example = "A parancssor")
    private String name;

    @JsonProperty
    @Size(max = 255)
    @Schema(description = "url of the lesson, not required", example = "https://e-learning.training360.com/courses/take/verziokezeles-es-kozos-munka-git-segitsegevel/lessons/17013055-a-parancssor")
    private String url;
}
