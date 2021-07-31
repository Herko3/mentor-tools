package mentortools.registration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRegistrationCommand {

    @Schema(description = "the id of a student",example = "1")
    private long studentId;

    @Schema(description = "the new status", example = "EXITED")
    private RegistrationStatus status;
}
