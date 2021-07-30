package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingClassDto {

    private Long id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
