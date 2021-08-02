package mentortools.lessons;

import lombok.AllArgsConstructor;
import mentortools.modules.Module;
import mentortools.modules.ModuleService;
import mentortools.trainingclass.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {

    private LessonRepository repository;
    private ModuleService moduleService;

    private ModelMapper mapper;

    public List<LessonDto> listLessons(long id) {
        return repository.findAllByModuleId(id).stream()
                .map(l -> mapper.map(l, LessonDto.class))
                .toList();
    }

    public LessonDto findLesson(long id, long lessonId) {
        return mapper.map(getLessonByIds(id, lessonId), LessonDto.class);
    }

    public Lesson getLessonByIds(long id, long lessonId) {
        return repository.findLessonByModuleIdAndId(id, lessonId).orElseThrow(() -> new NotFoundException(URI.create("lessons/not-found"), "Lesson not found with id: " + lessonId));
    }

    @Transactional
    public LessonDto createLesson(long id, CreateLessonCommand command) {
        Lesson lesson = new Lesson(command.getName(), command.getUrl());
        Module module = moduleService.getModule(id);
        module.addLesson(lesson);
        repository.save(lesson);
        return mapper.map(lesson, LessonDto.class);
    }

    public void deleteLessonById(long id, long lessonId) {
        Lesson lesson = getLessonByIds(id, lessonId);
        repository.delete(lesson);
    }

    @Transactional
    public LessonDto updateLesson(long id, long lessonId, UpdateLessonCommand command) {
        Lesson lesson = getLessonByIds(id, lessonId);
        String name = command.getName();
        if (name != null && !name.isBlank()) {
            lesson.setName(name);
        }
        String url = command.getUrl();
        if (url != null && !url.isBlank()) {
            lesson.setUrl(url);
        }
        return mapper.map(lesson,LessonDto.class);
    }
}
