package mentortools.modules;

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
public class UpdateModuleCommand {

    @Size(max = 255)
    @JsonProperty
    @Schema(description = "the name of the module, not required", example = "Version Control")
    private String name;

    @Size(max = 255)
    @JsonProperty
    @Schema(description = "the url of the module, not required", example = "https://e-learning.training360.com/courses/take/verziokezeles-es-kozos-munka-git-segitsegevel/")
    private String url;
}
