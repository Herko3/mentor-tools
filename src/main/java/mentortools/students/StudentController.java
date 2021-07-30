package mentortools.students;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/students")
@Tag(name = "Operations on students")
public class StudentController {

    private final StudentService service;

    @GetMapping
    @Operation(summary = "Gives back all of the students")
    public List<StudentDto> listStudents() {
        return service.listStudents();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gives back an exact student")
    @ApiResponse(responseCode = "404", description = "Student not found")
    public StudentDto getStudentById(@PathVariable("id") long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "adds a student to the database")
    public StudentDto createStudent(@Valid @RequestBody CreateStudentCommand command) {
        return service.createStudent(command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes a student")
    public void deleteById(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "updates a student")
    @ApiResponse(responseCode = "404", description = "student not found")
    public StudentDto updateStudent(@PathVariable("id") long id, @Valid @RequestBody UpdateStudentCommand command) {
        return service.updateStudent(id, command);
    }
}
