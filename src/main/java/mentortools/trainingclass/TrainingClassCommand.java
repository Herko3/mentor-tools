package mentortools.trainingclass;

import java.time.LocalDateTime;

public interface TrainingClassCommand {

    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
}
