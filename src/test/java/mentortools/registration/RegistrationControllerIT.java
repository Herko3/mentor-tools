package mentortools.registration;

import mentortools.students.CreateStudentCommand;
import mentortools.students.StudentDto;
import mentortools.trainingclass.CreateTrainingClassCommand;
import mentortools.trainingclass.TrainingClassDto;
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
@Sql(statements = "delete from registration; delete from students; delete from training_classes")
class RegistrationControllerIT {

    StudentDto student;
    StudentDto student2;
    TrainingClassDto trainingClass;
    TrainingClassDto trainingClass2;

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init(){
        student = template.postForObject("/api/students", new CreateStudentCommand(
                "Jack Doe", "jackie@example.org", "jackie", "working"), StudentDto.class);

        student2 = template.postForObject("/api/students", new CreateStudentCommand(
                "John Doe", "jackie@example.org", "jackie", "working"), StudentDto.class);

        trainingClass = template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);

        trainingClass2 = template.postForObject("/api/training_classes", new CreateTrainingClassCommand("Újratervezés 2.0",
                LocalDateTime.of(2020, 10, 5, 8, 0),
                LocalDateTime.of(2021, 4, 6, 15, 0)), TrainingClassDto.class);
    }

    @Test
    void testCreate(){
        RegistrationWithStudentDto result = template.postForObject("/api/training_classes/" +  trainingClass.getId() + "/registrations",
                new CreateRegistrationCommand(student.getId()),RegistrationWithStudentDto.class);

        assertEquals(RegistrationStatus.ACTIVE,result.getStatus());
        assertEquals("Jack Doe",result.getStudent().getName());
    }

    @Test
    void testInvalidDoublePost(){
        template.postForObject("/api/training_classes/" +  trainingClass.getId() + "/registrations",
                new CreateRegistrationCommand(student.getId()),RegistrationWithStudentDto.class);

        Problem problem = template.postForObject("/api/training_classes/" +  trainingClass.getId() + "/registrations",
                new CreateRegistrationCommand(student.getId()),Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }

    @Test
    void testUpdate(){
        template.postForObject("/api/training_classes/" +  trainingClass.getId() + "/registrations",
                new CreateRegistrationCommand(student.getId()),RegistrationWithStudentDto.class);

        RegistrationWithStudentDto result = template.exchange("/api/training_classes/" + trainingClass.getId() + "/registrations",
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateRegistrationCommand(student.getId(), RegistrationStatus.EXITED)),
                new ParameterizedTypeReference<RegistrationWithStudentDto>() {
                })
                .getBody();

        assertEquals(RegistrationStatus.EXITED,result.getStatus());
    }

    @Test
    void testUpdateWithoutRegistration(){
        Problem result = template.exchange("/api/training_classes/" + trainingClass.getId() + "/registrations",
                        HttpMethod.PUT,
                        new HttpEntity<>(new UpdateRegistrationCommand(student.getId(), RegistrationStatus.EXITED)),
                        new ParameterizedTypeReference<Problem>() {
                        })
                .getBody();

        assertEquals(Status.NOT_FOUND,result.getStatus());
    }

    @Test
    void testStudentList(){
        template.postForObject("/api/training_classes/" +  trainingClass.getId() + "/registrations",
                new CreateRegistrationCommand(student.getId()),RegistrationWithStudentDto.class);
        template.postForObject("/api/training_classes/" +  trainingClass.getId() + "/registrations",
                new CreateRegistrationCommand(student2.getId()),RegistrationWithStudentDto.class);

        List<RegistrationWithStudentDto> result = template.exchange("/api/training_classes/" + trainingClass.getId() + "/registrations",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<RegistrationWithStudentDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(RegistrationWithStudentDto::getStudent)
                .extracting(StudentRegisteredDto::getName)
                .containsExactly("Jack Doe","John Doe");
    }

    @Test
    void testClassList(){
        template.postForObject("/api/training_classes/" +  trainingClass.getId() + "/registrations",
                new CreateRegistrationCommand(student.getId()),RegistrationWithStudentDto.class);
        template.postForObject("/api/training_classes/" +  trainingClass2.getId() + "/registrations",
                new CreateRegistrationCommand(student.getId()),RegistrationWithStudentDto.class);

        List<RegistrationWithClassesDto> result = template.exchange("/api/students/" + student.getId() + "/registrations",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<RegistrationWithClassesDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(RegistrationWithClassesDto::getTrainingClass)
                .extracting(TrainingClassRegisteredDto::getName)
                .contains("Újratervezés 2.0");
    }


}