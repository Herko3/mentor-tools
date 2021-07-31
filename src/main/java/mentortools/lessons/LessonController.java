package mentortools.lessons;

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
@RequestMapping("api/modules/{id}/lessons")
@Tag(name = "Operation on the lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService service;

    @GetMapping
    @Operation(summary = "gives back all of the lessons of a module")
    @ApiResponse(responseCode = "404", description = "module not found")
    public List<LessonDto> listLessons(@PathVariable ("id") long id){
        return service.listLessons(id);
    }

    @GetMapping("/{lessonId}")
    @Operation(summary = "gives back a single lesson of a module")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "module not found"),
            @ApiResponse(responseCode = "404", description = "lesson not found")})
    public LessonDto findLesson(@PathVariable ("id") long id, @PathVariable ("lessonId") long lessonId){
        return service.findLesson(id,lessonId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a new lesson for the module")
    @ApiResponse(responseCode = "404", description = "module not found")
    public LessonDto createLesson(@PathVariable ("id") long id, @Valid @RequestBody CreateLessonCommand command){
        return service.createLesson(id,command);
    }

    @DeleteMapping("/{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes a single lesson")
    @ApiResponse(responseCode = "404",description = "module not found")
    public void deleteLessonById(@PathVariable ("id") long id, @PathVariable ("lessonId") long lessonId){
        service.deleteLessonById(id,lessonId);
    }

    @PutMapping("/{lessonId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "updates a single lesson")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "module not found"),
            @ApiResponse(responseCode = "404", description = "lesson not found")})
    public LessonDto updateLesson(@PathVariable ("id") long id, @PathVariable ("lessonId") long lessonId, @Valid @RequestBody UpdateLessonCommand command){
        return service.updateLesson(id,lessonId,command);
    }
}
