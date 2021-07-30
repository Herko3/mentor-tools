package mentortools.trainingclass;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class AfterValidator implements ConstraintValidator<After, TrainingClassCommand> {

    @Override
    public boolean isValid(TrainingClassCommand command, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime start = command.getStartTime();
        LocalDateTime end = command.getEndTime();

        if (start != null && end != null) {
            return start.isBefore(end);
        }
        return true;

    }

}
