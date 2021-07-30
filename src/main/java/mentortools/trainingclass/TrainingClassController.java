package mentortools.trainingclass;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/training_classes")
@RequiredArgsConstructor
@Tag(name = "Operations on Training classes")
public class TrainingClassController {

    private final TrainingClassService service;

    @GetMapping
    @Operation(summary = "gives back all classes")
    public List<TrainingClassDto> listTrainingClasses() {
        return service.listClasses();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates 1 class")
    public TrainingClassDto createClass(@Valid @RequestBody CreateTrainingClassCommand command) {
        return service.creatClass(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "updates 1 or more value in a single class")
    @ApiResponse(responseCode = "404", description = "Class not found")
    public TrainingClassDto updateClass(@PathVariable("id") long id, @Valid @RequestBody UpdateTrainingClassCommand command) {
        return service.updateClass(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes a single class")
    public void deleteById(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "gives back a single class with the same id")
    @ApiResponse(responseCode = "404", description = "Class not found")
    public TrainingClassDto findById(@PathVariable("id") long id) {
        return service.findById(id);
    }
}
