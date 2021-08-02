package mentortools.registration;

import lombok.AllArgsConstructor;
import mentortools.students.StudentService;
import mentortools.trainingclass.NotFoundException;
import mentortools.trainingclass.TrainingClassService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationService {

    private RegistrationRepository registrationRepository;
    private StudentService studentService;
    private TrainingClassService trainingClassService;

    private ModelMapper mapper;

    public List<RegistrationWithStudentDto> getRegistrationsForClass(long id) {
        return registrationRepository.findRegistrationsByTrainingClass_Id(id).stream()
                .map(r -> mapper.map(r, RegistrationWithStudentDto.class))
                .toList();
    }

    @Transactional
    public RegistrationWithStudentDto createRegistration(long id, CreateRegistrationCommand command) {
        checkIfExists(id, command);

        Registration registration = new Registration(RegistrationStatus.ACTIVE);
        trainingClassService.addRegistration(id, registration);
        studentService.addRegistration(command.getStudentId(), registration);
        registrationRepository.save(registration);
        return mapper.map(registration, RegistrationWithStudentDto.class);
    }

    public List<RegistrationWithClassesDto> getClassesByStudent(long id) {
        return registrationRepository.findRegistrationsByStudent_Id(id).stream()
                .map(r -> mapper.map(r, RegistrationWithClassesDto.class))
                .toList();
    }

    @Transactional
    public RegistrationWithStudentDto updateRegistration(long id, UpdateRegistrationCommand command) {
        Registration registration = registrationRepository.findRegistrationByTrainingClass_IdAndStudent_Id(id, command.getStudentId());
        if (registration == null) {
            throw new NotFoundException(URI.create("registrations/not-found"), "Registration not found");
        }

        registration.setStatus(command.getStatus());
        return mapper.map(registration, RegistrationWithStudentDto.class);
    }

    private void checkIfExists(long id, CreateRegistrationCommand command) {
        Registration check = registrationRepository.findRegistrationByTrainingClass_IdAndStudent_Id(id, command.getStudentId());
        if (check != null) {
            throw new DataAlreadyExistsException(URI.create("registration/already-exist"), "This registration is already exists");
        }
    }
}
