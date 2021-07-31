package mentortools.modules;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class ModuleNotFoundException extends AbstractThrowableProblem {

    public ModuleNotFoundException(long id) {
        super(URI.create("module/not-found"),
                "Not found",
                Status.NOT_FOUND,
                "module not found with id: " + id);
    }
}
