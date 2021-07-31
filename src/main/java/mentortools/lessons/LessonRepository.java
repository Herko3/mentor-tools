package mentortools.lessons;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByModuleId(long id);
    Optional<Lesson> findLessonByModuleIdAndId(long id, long lessonId);
}
