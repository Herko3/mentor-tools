package mentortools.completion;

import lombok.AllArgsConstructor;
import mentortools.lessons.Lesson;
import mentortools.lessons.LessonRepository;
import mentortools.registration.DataAlreadyExistsException;
import mentortools.students.Student;
import mentortools.students.StudentService;
import mentortools.trainingclass.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class LessonCompletionService {

    private LessonCompletionRepository repository;
    private StudentService studentService;
    private LessonRepository lessonRepository;

    private ModelMapper mapper;

    public List<LessonCompletionDto> listCompletions(long id) {
        return repository.findAllByStudent_Id(id).stream()
                .map(lc -> mapper.map(lc, LessonCompletionDto.class))
                .toList();
    }

    public LessonCompletionDto getCompletionById(long id, long compId) {
        return mapper.map(getLessonCompletionByIds(id, compId), LessonCompletionDto.class);
    }

    private LessonCompletion getLessonCompletionByIds(long id, long compId) {
        return repository.findByStudent_IdAndId(id, compId).orElseThrow(() -> new NotFoundException(URI.create("lesson-completions/not-found"), "Lesson completion not found with id: " + compId));
    }

    @Transactional
    public LessonCompletionDto createCompletion(long id, CreateLessonCompletionCommand command) {
        checkIfExists(id, command.getLessonId());
        Student student = studentService.getStudentById(id);
        Lesson lesson = getLessonById(command.getLessonId());
        LessonCompletion completion = new LessonCompletion(student, lesson);
        repository.save(completion);
        return mapper.map(completion, LessonCompletionDto.class);
    }

    @Transactional
    public LessonCompletionDto updateCompletion(long id, long compId, UpdateLessonCompletionCommand command) {
        LessonCompletion completion = getLessonCompletionByIds(id, compId);

        Long updateLessonId = command.getLessonId();
        if (updateLessonId != null && !Objects.equals(completion.getLesson().getId(), updateLessonId)) {
            checkIfExists(id, updateLessonId);

            Lesson lesson = getLessonById(updateLessonId);
            completion.setLesson(lesson);
        }

        completion.update(command);
        return mapper.map(completion, LessonCompletionDto.class);
    }

    public void deleteCompletionById(long id, long compId) {
        LessonCompletion completion = getLessonCompletionByIds(id, compId);
        repository.delete(completion);
    }

    private void checkIfExists(long id, long lessonId) {
        LessonCompletion completion = repository.findByStudent_IdAndLesson_Id(id, lessonId);
        if (completion != null) {
            throw new DataAlreadyExistsException(URI.create("students/completion-already-exists"), "this completion is already exists");
        }
    }

    private Lesson getLessonById(long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new NotFoundException(URI.create("lesson-completions/not-found"), "Lesson completion not found with id: " + id));
    }
}
