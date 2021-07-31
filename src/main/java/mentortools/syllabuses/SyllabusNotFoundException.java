package mentortools.syllabuses;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class SyllabusNotFoundException extends AbstractThrowableProblem {

    public SyllabusNotFoundException(long id) {
        super(URI.create("syllabuses/not-found"),
                "not found",
                Status.NOT_FOUND,
                "Syllabus not found with id: " + id);

    }
}
