package mentortools.students;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student student;

    @BeforeEach
    void init(){
        student = new Student("Jack Doe", "jackie@org.org","Jackie","working");
    }

    @Test
    void updateWithInvalidName(){
        student.update(null,"jacki@example.org","jacki","working");

        assertEquals("Jack Doe", student.getName());
    }

    @Test
    void updateWithInvalidEmail(){
        student.update("Jackie","","jacki","working");

        assertEquals("jackie@org.org", student.getEmail());
    }

    @Test
    void updateWithNullGithub(){
        student.update("Jackie","jacki@example.org",null,"working");

        assertEquals("Jackie", student.getGithubUserName());
    }

    @Test
    void updateWithNullDetail(){
        student.update("Jackie","jacki@example.org","jacki",null);

        assertEquals("working", student.getDetails());
    }

}