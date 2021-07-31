package mentortools.syllabuses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mentortools.modules.AddModuleCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/syllabuses")
@RequiredArgsConstructor
@Tag(name = "operations on syllabuses")
public class SyllabusController {

    private final SyllabusService service;

    @GetMapping
    @Operation(summary = "gives back the syllabuses")
    public List<SyllabusDto> listSyllabuses() {
        return service.listSyllabuses();
    }

    @GetMapping("/{id}")
    @Operation(summary = "gives back an exact syllabus")
    @ApiResponse(responseCode = "404", description = "Syllabus not found")
    public SyllabusDto getSyllabus(@PathVariable("id") long id) {
        return service.getSyllabus(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a new syllabus")
    public SyllabusDto createSyllabus(@Valid @RequestBody CreateSyllabusCommand command) {
        return service.createSyllabus(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "updates syllabus")
    @ApiResponse(responseCode = "404", description = "Syllabus not found")
    public SyllabusDto updateSyllabus(@PathVariable("id") long id, @RequestBody UpdateSyllabusCommand command) {
        return service.updateSyllabus(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes syllabus")
    @ApiResponse(responseCode = "404", description = "Syllabus not found")
    public void deleteSyllabus(@PathVariable("id") long id) {
        service.deleteSyllabus(id);
    }

    @PostMapping("/{id}/modules")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "adds a module to the syllabus")
    @ApiResponse(responseCode = "404", description = "Syllabus not found")
    public SyllabusWithModulesDto addModule(@PathVariable ("id") long id, @Valid @RequestBody AddModuleCommand command){
        return service.addModule(id, command);
    }

    @GetMapping("/{id}/modules")
    @Operation(summary = "gives back the modules for a syllabus")
    @ApiResponse(responseCode = "404", description = "Syllabus not found")
    public SyllabusWithModulesDto getSyllabusWithModules(@PathVariable ("id") long id){
        return service.getSyllabusWithModules(id);
    }
}
