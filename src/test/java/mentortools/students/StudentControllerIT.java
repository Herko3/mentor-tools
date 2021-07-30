package mentortools.students;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from students")
class StudentControllerIT {

    @Autowired
    TestRestTemplate template;

    @Test
    void testSaveThenList() {
        template.postForObject("/api/students", new CreateStudentCommand(
                "Jack Doe", "jackie@example.org", "jackie", "working"), StudentDto.class);

        template.postForObject("/api/students", new CreateStudentCommand(
                "John Doe", "jackie@example.org", "jackie", "working"), StudentDto.class);

        List<StudentDto> result = template.exchange("/api/students", HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<StudentDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(StudentDto::getName)
                .containsExactly("Jack Doe", "John Doe");
    }

    @Test
    void testDelete() {
        template.postForObject("/api/students", new CreateStudentCommand(
                "Jack Doe", "jackie@example.org", "jackie", "working"), StudentDto.class);

        StudentDto toDelete = template.postForObject("/api/students", new CreateStudentCommand(
                "John Doe", "jackie@example.org", "jonny", "working"), StudentDto.class);

        template.delete("/api/students/" + toDelete.getId());

        List<StudentDto> result = template.exchange("/api/students", HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<StudentDto>>() {
                        })
                .getBody();

        assertThat(result)
                .hasSize(1)
                .extracting(StudentDto::getGithubUserName)
                .containsOnly("jackie");
    }

    @Test
    void testFindById(){
        StudentDto post = template.postForObject("/api/students", new CreateStudentCommand(
                "Jack Doe", "jackie@example.org", "jackie", "working"), StudentDto.class);

        StudentDto result = template.getForObject("/api/students/" + post.getId(), StudentDto.class);

        assertEquals("working",result.getDetails());
    }

    @Test
    void testUpdate(){
        StudentDto post = template.postForObject("/api/students", new CreateStudentCommand(
                "Jack Doe", "jackie@example.org", "jackie", "working"), StudentDto.class);

        template.put("/api/students/" + post.getId(),new UpdateStudentCommand("John Doe","Johnny@example.org","jonnyBoi",""));

        StudentDto result = template.getForObject("/api/students/" + post.getId(), StudentDto.class);

        assertEquals("John Doe",result.getName());
        assertEquals("",result.getDetails());
    }
}