package mentortools.syllabuses;

import mentortools.trainingclass.AddOrUpdateSyllabusCommand;
import mentortools.trainingclass.CreateTrainingClassCommand;
import mentortools.trainingclass.TrainingClassDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from training_classes; delete from syllabuses")
class SyllabusControllerIT {

    TrainingClassDto trainingClass;

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {
        trainingClass = template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);
    }

    @Test
    void testCreateThenList() {
        template.postForObject("/api/syllabuses", new CreateSyllabusCommand("JPA"), SyllabusDto.class);
        template.postForObject("/api/syllabuses", new CreateSyllabusCommand("JDBC"), SyllabusDto.class);

        List<SyllabusDto> result = template.exchange("/api/syllabuses",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<SyllabusDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(SyllabusDto::getName)
                .containsExactly("JPA", "JDBC");
    }

    @Test
    void createThenGet() {
        SyllabusDto post = template.postForObject("/api/syllabuses", new CreateSyllabusCommand("JPA"), SyllabusDto.class);

        SyllabusDto result = template.getForObject("/api/syllabuses/" + post.getId(), SyllabusDto.class);

        assertEquals("JPA", result.getName());
    }

    @Test
    void addSyllabus() {
        SyllabusDto post = template.postForObject("/api/syllabuses", new CreateSyllabusCommand("JPA"), SyllabusDto.class);

        TrainingClassDto result = template.postForObject("/api/training_classes/" + trainingClass.getId() + "/syllabuses", new AddOrUpdateSyllabusCommand(post.getId()), TrainingClassDto.class);

        assertEquals("JPA", result.getSyllabus().getName());
        assertEquals(post.getId(), result.getSyllabus().getId());
    }

    @Test
    void deleteSyllabus() {
        SyllabusDto post = template.postForObject("/api/syllabuses", new CreateSyllabusCommand("JPA"), SyllabusDto.class);

        TrainingClassDto addedSyllabus = template.postForObject("/api/training_classes/" + trainingClass.getId() + "/syllabuses", new AddOrUpdateSyllabusCommand(post.getId()), TrainingClassDto.class);

        assertEquals("JPA", addedSyllabus.getSyllabus().getName());

        template.delete("/api/syllabuses/" + post.getId());

        TrainingClassDto result = template.getForObject("/api/training_classes/" + addedSyllabus.getId(), TrainingClassDto.class);

        assertNull(result.getSyllabus());

    }

    @Test
    void updateSyllabus() {
        SyllabusDto post = template.postForObject("/api/syllabuses", new CreateSyllabusCommand("JPA"), SyllabusDto.class);

        template.put("/api/syllabuses/" + post.getId(), new UpdateSyllabusCommand("JDBC"));

        SyllabusDto result = template.getForObject("/api/syllabuses/" + post.getId(),SyllabusDto.class);

        assertEquals("JDBC",result.getName());
    }

    @Test
    void updateTrainingClassSyllabus(){
        SyllabusDto first = template.postForObject("/api/syllabuses", new CreateSyllabusCommand("JPA"), SyllabusDto.class);

        TrainingClassDto before = template.postForObject("/api/training_classes/" + trainingClass.getId() + "/syllabuses", new AddOrUpdateSyllabusCommand(first.getId()), TrainingClassDto.class);

        assertEquals("JPA",before.getSyllabus().getName());

        SyllabusDto second = template.postForObject("/api/syllabuses", new CreateSyllabusCommand("JDBC"), SyllabusDto.class);

        TrainingClassDto after = template.postForObject("/api/training_classes/" + trainingClass.getId() + "/syllabuses", new AddOrUpdateSyllabusCommand(second.getId()), TrainingClassDto.class);

        assertEquals("JDBC",after.getSyllabus().getName());
    }


}