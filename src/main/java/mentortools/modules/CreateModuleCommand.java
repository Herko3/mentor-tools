package mentortools.modules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateModuleCommand {

    @NotNull(message = "name must not be empty")
    @Size(max = 255)
    @Schema(description = "the name of the module", example = "Version Control")
    private String name;

    @NotNull(message = "url must not be empty")
    @Size(max = 255)
    @Schema(description = "the url of the module", example = "https://e-learning.training360.com/courses/take/verziokezeles-es-kozos-munka-git-segitsegevel/")
    private String url;
}
