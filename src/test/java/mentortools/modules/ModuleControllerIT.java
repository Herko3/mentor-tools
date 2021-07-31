package mentortools.modules;

import mentortools.syllabuses.CreateSyllabusCommand;
import mentortools.syllabuses.SyllabusDto;
import mentortools.syllabuses.SyllabusWithModulesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from modules")
class ModuleControllerIT {

    SyllabusDto syllabus;

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {
        syllabus = template.postForObject("/api/syllabuses", new CreateSyllabusCommand("JPA"), SyllabusDto.class);
    }

    @Test
    void testSaveThenList() {
        template.postForObject("/api/modules", new CreateModuleCommand("Git", "git.com"), ModuleDto.class);
        template.postForObject("/api/modules", new CreateModuleCommand("Git2", "git2.com"), ModuleDto.class);

        List<ModuleDto> result = template.exchange("/api/modules",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ModuleDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(ModuleDto::getName)
                .containsExactly("Git", "Git2");
    }

    @Test
    void testGetById() {
        ModuleDto post = template.postForObject("/api/modules", new CreateModuleCommand("Git", "git.com"), ModuleDto.class);

        ModuleDto result = template.getForObject("/api/modules/" + post.getId(), ModuleDto.class);

        assertEquals("git.com", result.getUrl());
    }

    @Test
    void testNotFound() {
        Problem problem = template.getForObject("/api/modules/156456465", Problem.class);

        assertEquals(Status.NOT_FOUND, problem.getStatus());
    }

    @Test
    void testUpdate() {
        ModuleDto post = template.postForObject("/api/modules", new CreateModuleCommand("Git", "git.com"), ModuleDto.class);

        template.put("/api/modules/" + post.getId(), new UpdateModuleCommand("GitHub", "Github.com"));

        ModuleDto result = template.getForObject("/api/modules/" + post.getId(), ModuleDto.class);

        assertEquals("GitHub", result.getName());
    }

    @Test
    void testAddModule(){
        ModuleDto module = template.postForObject("/api/modules", new CreateModuleCommand("Git", "git.com"), ModuleDto.class);

        SyllabusWithModulesDto result = template.postForObject("/api/syllabuses/" + syllabus.getId() + "/modules",
                new AddModuleCommand(module.getId()),SyllabusWithModulesDto.class);

        assertTrue(result.getModules().contains(module));
    }

    @Test
    void testDeleteModule(){
        ModuleDto module = template.postForObject("/api/modules", new CreateModuleCommand("Git", "git.com"), ModuleDto.class);

        template.postForObject("/api/syllabuses/" + syllabus.getId() + "/modules",
                new AddModuleCommand(module.getId()),SyllabusWithModulesDto.class);

        template.delete("/api/modules/" + module.getId());

        SyllabusWithModulesDto result = template.getForObject("/api/syllabuses/" + syllabus.getId() + "/modules",SyllabusWithModulesDto.class);

        assertEquals(Collections.emptyList(),result.getModules());
    }
}