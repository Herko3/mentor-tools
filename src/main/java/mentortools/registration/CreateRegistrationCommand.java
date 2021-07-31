package mentortools.registration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateRegistrationCommand {

    @NotNull
    @Schema(description = "the id opf an existing student", example = "1")
    private Long studentId;
}
