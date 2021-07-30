package mentortools.trainingclass;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class InvalidEndTimeException extends AbstractThrowableProblem {

    public InvalidEndTimeException(String message) {
        super(URI.create("classes/invalid-time"),
                "Invalid end time",
                Status.BAD_REQUEST,
                message);
    }
}
