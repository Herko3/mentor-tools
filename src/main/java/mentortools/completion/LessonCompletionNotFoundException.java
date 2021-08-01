package mentortools.completion;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class LessonCompletionNotFoundException extends AbstractThrowableProblem {

    public LessonCompletionNotFoundException(long id) {
        super(URI.create("students/completion-not-found"),
                "not found",
                Status.NOT_FOUND,
                "Completion not found with id: " + id);
    }
}
