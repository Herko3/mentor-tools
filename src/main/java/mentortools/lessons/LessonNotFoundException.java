package mentortools.lessons;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class LessonNotFoundException extends AbstractThrowableProblem {
    public LessonNotFoundException(long id) {
        super(URI.create("modules/lesson-not-found"),
                "not found",
                Status.NOT_FOUND,
                "no lesson in the module with id:" + id);
    }
}
