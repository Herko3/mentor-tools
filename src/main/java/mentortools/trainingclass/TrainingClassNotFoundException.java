package mentortools.trainingclass;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class TrainingClassNotFoundException extends AbstractThrowableProblem {

    public TrainingClassNotFoundException(long id) {
        super(URI.create("classes/not-found"),
                "not found",
                Status.NOT_FOUND,
                "Training Class not found with id: " + id);

    }
}
