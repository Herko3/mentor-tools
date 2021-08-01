package mentortools.completion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonCompletionRepository extends JpaRepository<LessonCompletion,Long> {

    public List<LessonCompletion> findAllByStudent_Id(long id);
    public Optional<LessonCompletion> findByStudent_IdAndId(long id, long compId);
    public LessonCompletion findByStudent_IdAndLesson_Id(long id, long lessonId);
}
