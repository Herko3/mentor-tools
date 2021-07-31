package mentortools.registration;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class DataAlreadyExistsException extends AbstractThrowableProblem {

    public DataAlreadyExistsException(String message) {
        super(URI.create("registration/already-exist"),
                "Data already exists",
                Status.BAD_REQUEST,
                message);
    }
}
