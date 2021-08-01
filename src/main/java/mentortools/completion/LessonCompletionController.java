package mentortools.completion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/students/{id}/lesson-completion")
@Tag(name = "Operations on Lesson Completions")
@RequiredArgsConstructor
public class LessonCompletionController {

    private final LessonCompletionService service;

    @GetMapping
    @Operation(summary = "gives back all the lessoncompletions for a single student")
    public List<LessonCompletionDto> listCompletions(@PathVariable("id") long id) {
        return service.listCompletions(id);
    }

    @GetMapping("/{comp_id}")
    @Operation(summary = "gives back a single completion of the student")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "404", description = "lesson completion not found")})
    public LessonCompletionDto getCompletionById(@PathVariable("id") long id, @PathVariable("comp_id") long compId) {
        return service.getCompletionById(id, compId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "adds a completion to a student")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "404", description = "lesson not found")})
    public LessonCompletionDto createCompletion(@PathVariable("id") long id, @Valid @RequestBody CreateLessonCompletionCommand command) {
        return service.createCompletion(id, command);
    }

    @PutMapping("/{comp_id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "updates a single completion of the student")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "404", description = "lesson completion not found"),
            @ApiResponse(responseCode = "404", description = "lesson not found")})
    public LessonCompletionDto updateCompletion(@PathVariable("id") long id, @PathVariable("comp_id") long compId, @Valid @RequestBody UpdateLessonCompletionCommand command) {
        return service.updateCompletion(id, compId, command);
    }

    @DeleteMapping("/{comp_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes a single completion of the student")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "404", description = "lesson completion not found")})
    public void deleteCompletionById(@PathVariable("id") long id, @PathVariable("comp_id") long compId){
        service.deleteCompletionById(id,compId);
    }
}
