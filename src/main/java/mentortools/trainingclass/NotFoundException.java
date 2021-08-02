package mentortools.trainingclass;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class NotFoundException extends AbstractThrowableProblem {

    public NotFoundException(URI uri, String message) {
        super(uri,
                "not found",
                Status.NOT_FOUND,
                message);

    }
}
