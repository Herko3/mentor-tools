package mentortools.modules;

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
@RequestMapping("/api/modules")
@Tag(name = "Operations on modules")
public class ModuleController {

    private final ModuleService service;

    @GetMapping
    @Operation(summary = "gives back all of the modules")
    public List<ModuleDto> listModules() {
        return service.listModules();
    }

    @GetMapping("/{id}")
    @Operation(summary = "gives back an exact module")
    public ModuleDto findModuleById(@PathVariable("id") long id) {
        return service.findModuleById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a module")
    public ModuleDto createModule(@Valid @RequestBody CreateModuleCommand command) {
        return service.createModule(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "updates a single module")
    @ApiResponse(responseCode = "404", description = "module not found")
    public ModuleDto updateModule(@PathVariable("id") long id, @Valid @RequestBody UpdateModuleCommand command) {
        return service.updateModule(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes a single module")
    @ApiResponse(responseCode = "404", description = "module not found")
    public void deleteById(@PathVariable ("id") long id){
        service.deleteById(id);
    }
}
