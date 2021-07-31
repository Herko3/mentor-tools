package mentortools.registration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService service;

    @GetMapping("/api/training_classes/{id}/registrations")
    @Operation(summary = "gives back the students of a class")
    public List<RegistrationWithStudentDto> getRegistrationsForClass(@PathVariable ("id") long id){
        return service.getRegistrationsForClass(id);
    }

    @PostMapping("/api/training_classes/{id}/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a registration for a class and a student")
    @ApiResponses(value = {@ApiResponse(responseCode = "404",description = "Student or Training Class not found"),
            @ApiResponse(responseCode = "400", description = "registration alrady exists") })
    public RegistrationWithStudentDto createRegistration(@PathVariable ("id") long id, @RequestBody CreateRegistrationCommand command){
        return service.createRegistration(id,command);
    }

    @GetMapping("/api/students/{id}/registrations")
    @Operation(summary = "gives back all of a classes of a student")
    public List<RegistrationWithClassesDto> getClassesByStudent(@PathVariable ("id") long id){
        return service.getClassesByStudent(id);
    }

    @PutMapping("/api/training_classes/{id}/registrations")
    @Operation(summary = "changes the status of a registration")
    @ApiResponse(responseCode = "404", description = "registration not found")
    public RegistrationWithStudentDto updateRegistration(@PathVariable ("id") long id, @RequestBody UpdateRegistrationCommand command){
        return service.updateRegistration(id,command);
    }
}
