package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import mentortools.registration.Registration;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainingClassService {

    private TrainingClassRepository repository;

    private ModelMapper mapper;

    public List<TrainingClassDto> listClasses() {
        return repository.findAll().stream()
                .map(t -> mapper.map(t, TrainingClassDto.class))
                .toList();
    }

    public TrainingClassDto creatClass(CreateTrainingClassCommand command) {
        TrainingClass trainingClass = new TrainingClass(command.getName(), command.getStartTime(), command.getEndTime());
        repository.save(trainingClass);
        return mapper.map(trainingClass, TrainingClassDto.class);
    }

    @Transactional
    public TrainingClassDto updateClass(long id, UpdateTrainingClassCommand command) {
        TrainingClass trainingClass = getById(id);
        trainingClass.update(command.getName(), command.getStartTime(), command.getEndTime());

        return mapper.map(trainingClass, TrainingClassDto.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public TrainingClassDto findById(long id) {
        TrainingClass result = getById(id);
        return mapper.map(result, TrainingClassDto.class);
    }

    private TrainingClass getById(long id) {
        return repository.findById(id).orElseThrow(() -> new TrainingClassNotFoundException(id));
    }

    public TrainingClassDto addRegistration(long id, Registration registration){
        TrainingClass trainingClass = getById(id);
        trainingClass.addRegistration(registration);
        return mapper.map(trainingClass,TrainingClassDto.class);
    }
}
