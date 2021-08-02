package mentortools.syllabuses;

import lombok.AllArgsConstructor;
import mentortools.modules.AddModuleCommand;
import mentortools.modules.Module;
import mentortools.modules.ModuleRepository;
import mentortools.trainingclass.NotFoundException;
import mentortools.trainingclass.TrainingClassRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@Service
@AllArgsConstructor
public class SyllabusService {

    private SyllabusRepository repository;
    private TrainingClassRepository trainingClassRepository;
    private ModuleRepository moduleRepository;

    private ModelMapper mapper;

    public List<SyllabusDto> listSyllabuses() {
        return repository.findAll().stream()
                .map(s -> mapper.map(s, SyllabusDto.class))
                .toList();
    }

    public SyllabusDto getSyllabus(long id) {
        return mapper.map(findById(id), SyllabusDto.class);
    }

    public Syllabus findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(URI.create("syllabuses/not-found"), "syllabus not found with id: " + id));
    }

    public SyllabusDto createSyllabus(CreateSyllabusCommand command) {
        Syllabus syllabus = new Syllabus(command.getName());
        repository.save(syllabus);
        return mapper.map(syllabus, SyllabusDto.class);
    }

    @Transactional
    public SyllabusDto updateSyllabus(long id, UpdateSyllabusCommand command) {
        Syllabus syllabus = findById(id);
        syllabus.setName(command.getName());
        return mapper.map(syllabus, SyllabusDto.class);
    }

    @Transactional
    public void deleteSyllabus(long id) {
        trainingClassRepository.findAllBySyllabus_Id(id).stream()
                .forEach(t -> t.setSyllabus(null));
        repository.deleteById(id);
    }

    @Transactional
    public SyllabusWithModulesDto addModule(long id, AddModuleCommand command) {
        Syllabus syllabus = findById(id);
        Module module = moduleRepository.findById(command.getModuleId()).orElseThrow(()->new NotFoundException(URI.create("modules/not-found"), "Module not found with id: " + command.getModuleId()));
        syllabus.addModule(module);
        return mapper.map(syllabus,SyllabusWithModulesDto.class);
    }

    public SyllabusWithModulesDto getSyllabusWithModules(long id) {
        return mapper.map(findById(id),SyllabusWithModulesDto.class);
    }
}
