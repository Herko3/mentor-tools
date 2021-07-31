package mentortools.syllabuses;

import lombok.AllArgsConstructor;
import mentortools.trainingclass.TrainingClassRepository;
import mentortools.trainingclass.TrainingClassService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class SyllabusService {

    private SyllabusRepository repository;
    private TrainingClassRepository trainingClassRepository;

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
        return repository.findById(id).orElseThrow(() -> new SyllabusNotFoundException(id));
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
}
