package mentortools.lessons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLessonCommand {

    @NotBlank(message = "name cannot be empty")
    @Size(max = 255)
    @Schema(description = "the name of the lesson", example = "A parancssor")
    private String name;

    @NotBlank(message = "url cannot be empty")
    @Size(max = 255)
    @Schema(description = "url of the lesson", example = "https://e-learning.training360.com/courses/take/verziokezeles-es-kozos-munka-git-segitsegevel/lessons/17013055-a-parancssor")
    private String url;
}
