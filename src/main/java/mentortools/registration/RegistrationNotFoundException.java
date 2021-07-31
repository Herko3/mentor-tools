package mentortools.registration;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class RegistrationNotFoundException extends AbstractThrowableProblem {

    public RegistrationNotFoundException(String message){
        super(URI.create("/registration/not-found"),
                "Not found",
                Status.NOT_FOUND,
                message);
    }
}
