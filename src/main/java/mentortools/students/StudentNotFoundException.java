package mentortools.students;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class StudentNotFoundException extends AbstractThrowableProblem {

    public StudentNotFoundException(long id) {
        super(URI.create("students/not-found"),
                "not found",
                Status.NOT_FOUND,
                "Training Class not found with id: " + id);

    }
}
