package mentortools.trainingclass;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from training_classes")
class TrainingClassControllerIT {

    @Autowired
    TestRestTemplate template;

    @Test
    void testListClasses() {
        template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);

        template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés 2.0",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);

        List<TrainingClassDto> result = template.exchange("/api/training_classes",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TrainingClassDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(TrainingClassDto::getName)
                .containsExactly("Újratervezés", "Újratervezés 2.0");
    }

    @Test
    void testFindById() {
        TrainingClassDto post = template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);

        TrainingClassDto result = template.getForObject("/api/training_classes/" + post.getId(), TrainingClassDto.class);

        assertEquals("Újratervezés", result.getName());
    }

    @Test
    void testDeleteById() {
        template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);

        TrainingClassDto toDelete = template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés 2.0",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);

        List<TrainingClassDto> result = template.exchange("/api/training_classes",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TrainingClassDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(TrainingClassDto::getName)
                .containsExactly("Újratervezés", "Újratervezés 2.0");

        template.delete("/api/training_classes/" + toDelete.getId());

        result = template.exchange("/api/training_classes",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TrainingClassDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(1)
                .extracting(TrainingClassDto::getName)
                .containsOnly("Újratervezés");
    }

    @Test
    void updateWithValidData() {
        TrainingClassDto update = template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);

        template.put("/api/training_classes/" + update.getId(), new UpdateTrainingClassCommand("Újratervezés 2.0",
                LocalDateTime.of(2021, 10, 5, 8, 0),
                LocalDateTime.of(2022, 4, 6, 15, 0)));

        TrainingClassDto result = template.getForObject("/api/training_classes/" + update.getId(), TrainingClassDto.class);

        assertEquals("Újratervezés 2.0", result.getName());
        assertEquals(LocalDateTime.of(2021, 10, 5, 8, 0), result.getStartTime());
    }

    @Test
    void updateInvalidStartTimeAndNullEndTime() {
        TrainingClassDto update = template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);

        template.put("/api/training_classes/" + update.getId(), new UpdateTrainingClassCommand("Újratervezés 2.0",
                LocalDateTime.of(2021, 10, 5, 8, 0),
                null));

        TrainingClassDto result = template.getForObject("/api/training_classes/" + update.getId(), TrainingClassDto.class);

        assertEquals(LocalDateTime.of(2020, 10, 5, 8, 0), result.getStartTime());
    }

    @Test
    void updateInvalidEndTimeAndNullStartTime() {
        TrainingClassDto update = template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);

        template.put("/api/training_classes/" + update.getId(), new UpdateTrainingClassCommand("Újratervezés 2.0",
                null,
                LocalDateTime.of(2019, 10, 5, 8, 0)));

        TrainingClassDto result = template.getForObject("/api/training_classes/" + update.getId(), TrainingClassDto.class);

        assertEquals(LocalDateTime.of(2021, 4, 6, 15, 0), result.getEndTime());
    }

    @Test
    void createInvalidName(){
        Problem problem = template.postForObject("/api/training_classes",new CreateTrainingClassCommand("",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }

    @Test
    void createWithInvalidStart(){
        Problem problem = template.postForObject("/api/training_classes",new CreateTrainingClassCommand("Random",
                null,
                LocalDateTime.of(2021, 4, 6, 15, 0)), Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }
}