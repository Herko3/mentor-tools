package mentortools.lessons;

import mentortools.modules.CreateModuleCommand;
import mentortools.modules.ModuleDto;
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

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from modules; delete from lessons")
class LessonControllerIT {

    ModuleDto module;

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {
        module = template.postForObject("/api/modules", new CreateModuleCommand("Git", "git.com"), ModuleDto.class);
    }

    @Test
    void testCreateThenList() {
        template.postForObject("/api/modules/" + module.getId() + "/lessons",
                new CreateLessonCommand("Parancssori indítás", "pcs.training360.com"), LessonDto.class);

        template.postForObject("/api/modules/" + module.getId() + "/lessons",
                new CreateLessonCommand("Bash", "bash.training360.com"), LessonDto.class);

        List<LessonDto> result = template.exchange("/api/modules/" + module.getId() + "/lessons",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LessonDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(LessonDto::getName)
                .contains("Bash");
    }

    @Test
    void testGetById() {
        LessonDto post = template.postForObject("/api/modules/" + module.getId() + "/lessons",
                new CreateLessonCommand("Parancssori indítás", "pcs.training360.com"), LessonDto.class);

        LessonDto result = template.getForObject("/api/modules/" + module.getId() + "/lessons/" + post.getId(), LessonDto.class);

        assertEquals("pcs.training360.com", result.getUrl());
    }

    @Test
    void invalidGetById() {
        Problem problem = template.getForObject("/api/modules/" + module.getId() + "/lessons/4685465456", Problem.class);

        assertEquals(Status.NOT_FOUND, problem.getStatus());
    }

    @Test
    void testUpdate() {
        LessonDto post = template.postForObject("/api/modules/" + module.getId() + "/lessons",
                new CreateLessonCommand("Parancssori indítás", "pcs.training360.com"), LessonDto.class);

        template.put("/api/modules/" + module.getId() + "/lessons/" + post.getId(), new UpdateLessonCommand("Bash", "bash.git.com"));

        LessonDto result = template.getForObject("/api/modules/" + module.getId() + "/lessons/" + post.getId(), LessonDto.class);

        assertEquals("Bash", result.getName());
    }

    @Test
    void invalidDataUpdate() {
        LessonDto post = template.postForObject("/api/modules/" + module.getId() + "/lessons",
                new CreateLessonCommand("Parancssori indítás", "pcs.training360.com"), LessonDto.class);

        Problem problem = template.exchange("/api/modules/" + module.getId() + "/lessons/" + post.getId(),
                        HttpMethod.PUT,
                        new HttpEntity<>(null),
                        new ParameterizedTypeReference<Problem>() {
                        })
                .getBody();

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }

    @Test
    void testDelete() {
        LessonDto post = template.postForObject("/api/modules/" + module.getId() + "/lessons",
                new CreateLessonCommand("Parancssori indítás", "pcs.training360.com"), LessonDto.class);

        template.postForObject("/api/modules/" + module.getId() + "/lessons",
                new CreateLessonCommand("Bash", "bash.training360.com"), LessonDto.class);

        List<LessonDto> postDelete = template.exchange("/api/modules/" + module.getId() + "/lessons",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LessonDto>>() {
                        })
                .getBody();

        assertThat(postDelete)
                .hasSize(2)
                .extracting(LessonDto::getName)
                .contains("Parancssori indítás");

        template.delete("/api/modules/" + module.getId() + "/lessons/" + post.getId());

        List<LessonDto> result = template.exchange("/api/modules/" + module.getId() + "/lessons",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LessonDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(1)
                .extracting(LessonDto::getUrl)
                .containsExactly("bash.training360.com");
    }


}