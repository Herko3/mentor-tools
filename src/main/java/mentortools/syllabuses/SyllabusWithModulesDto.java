package mentortools.syllabuses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.modules.ModuleDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyllabusWithModulesDto {

    private long id;
    private String name;
    private List<ModuleDto> modules;
}
