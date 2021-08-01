package mentortools.registration;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class DataAlreadyExistsException extends AbstractThrowableProblem {

    public DataAlreadyExistsException(URI uri, String message) {
        super(uri,
                "Data already exists",
                Status.BAD_REQUEST,
                message);
    }
}
