package mentortools.completion;

import mentortools.lessons.CreateLessonCommand;
import mentortools.lessons.LessonDto;
import mentortools.modules.CreateModuleCommand;
import mentortools.modules.ModuleDto;
import mentortools.students.CreateStudentCommand;
import mentortools.students.StudentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from students; delete from modules; delete from lesson_completions; delete from lessons")
class LessonCompletionControllerIT {

    StudentDto student;
    ModuleDto module;
    LessonDto lesson1;
    LessonDto lesson2;

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {
        student = template.postForObject("/api/students", new CreateStudentCommand(
                "Jack Doe", "jackie@example.org", "jackie", "working"), StudentDto.class);

        module = template.postForObject("/api/modules", new CreateModuleCommand("Git", "git.com"), ModuleDto.class);

        lesson1 = template.postForObject("/api/modules/" + module.getId() + "/lessons",
                new CreateLessonCommand("Parancssori indítás", "pcs.training360.com"), LessonDto.class);

        lesson2 = template.postForObject("/api/modules/" + module.getId() + "/lessons",
                new CreateLessonCommand("Bash", "bash.training360.com"), LessonDto.class);
    }

    @Test
    void testCreateThenList() {
        LessonCompletionDto completion1 = template.postForObject("/api/students/" + student.getId() + "/lesson-completion",
                new CreateLessonCompletionCommand(lesson1.getId()), LessonCompletionDto.class);

        LessonCompletionDto completion2 = template.postForObject("/api/students/" + student.getId() + "/lesson-completion",
                new CreateLessonCompletionCommand(lesson2.getId()), LessonCompletionDto.class);

        List<LessonCompletionDto> result = template.exchange("/api/students/" + student.getId() + "/lesson-completion",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LessonCompletionDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(LessonCompletionDto::getId)
                .contains(completion1.getId(), completion2.getId());
    }

    @Test
    void testUpdateEverything() {
        LessonCompletionDto completion1 = template.postForObject("/api/students/" + student.getId() + "/lesson-completion",
                new CreateLessonCompletionCommand(lesson1.getId()), LessonCompletionDto.class);

        template.put("/api/students/" + student.getId() + "/lesson-completion/" + completion1.getId(),
                new UpdateLessonCompletionCommand(lesson2.getId(), CompletionStatus.COMPLETED, LocalDateTime.now(),
                        CompletionStatus.COMPLETED, LocalDateTime.now(), "github.com/"));

        LessonCompletionDto result = template.getForObject("/api/students/" + student.getId() + "/lesson-completion/" + completion1.getId(), LessonCompletionDto.class);

        assertEquals("github.com/", result.getCommitUrl());
        assertEquals(lesson2.getId(), result.getLesson().getId());
        assertEquals(CompletionStatus.COMPLETED, result.getVideoStatus());
        assertEquals(CompletionStatus.COMPLETED, result.getExerciseStatus());

    }

    @Test
    void testInvalidDoubleCreate() {
        template.postForObject("/api/students/" + student.getId() + "/lesson-completion",
                new CreateLessonCompletionCommand(lesson1.getId()), LessonCompletionDto.class);

        Problem problem = template.postForObject("/api/students/" + student.getId() + "/lesson-completion",
                new CreateLessonCompletionCommand(lesson1.getId()), Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }

    @Test
    void testUpdateMakesAlreadyExistedCompletion() {
        LessonCompletionDto completion = template.postForObject("/api/students/" + student.getId() + "/lesson-completion",
                new CreateLessonCompletionCommand(lesson1.getId()), LessonCompletionDto.class);

        template.postForObject("/api/students/" + student.getId() + "/lesson-completion",
                new CreateLessonCompletionCommand(lesson2.getId()), LessonCompletionDto.class);

        Problem problem = template.exchange("/api/students/" + student.getId() + "/lesson-completion/" + completion.getId(),
                        HttpMethod.PUT,
                        new HttpEntity<>(new UpdateLessonCompletionCommand(lesson2.getId(), null, null, null, null, null)),
                        new ParameterizedTypeReference<Problem>() {
                        })
                .getBody();

        assertEquals(Status.BAD_REQUEST, problem.getStatus());

    }

    @Test
    void testDelete() {
        LessonCompletionDto completion1 = template.postForObject("/api/students/" + student.getId() + "/lesson-completion",
                new CreateLessonCompletionCommand(lesson1.getId()), LessonCompletionDto.class);

        LessonCompletionDto completion2 = template.postForObject("/api/students/" + student.getId() + "/lesson-completion",
                new CreateLessonCompletionCommand(lesson2.getId()), LessonCompletionDto.class);

        List<LessonCompletionDto> postDelete = template.exchange("/api/students/" + student.getId() + "/lesson-completion",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LessonCompletionDto>>() {
                        })
                .getBody();

        assertThat(postDelete)
                .hasSize(2);

        template.delete("/api/students/" + student.getId() + "/lesson-completion/" + completion1.getId());

        List<LessonCompletionDto> result = template.exchange("/api/students/" + student.getId() + "/lesson-completion",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LessonCompletionDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(1);
    }
}