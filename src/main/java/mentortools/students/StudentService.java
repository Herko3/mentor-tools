package mentortools.students;

import lombok.AllArgsConstructor;
import mentortools.registration.CreateRegistrationCommand;
import mentortools.registration.Registration;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private StudentRepository repository;

    private ModelMapper mapper;

    public List<StudentDto> listStudents() {
        return repository.findAll().stream()
                .map(s -> mapper.map(s, StudentDto.class))
                .toList();
    }


    public StudentDto getById(long id) {
        Student student = getStudentById(id);
        return mapper.map(student, StudentDto.class);
    }

    public Student getStudentById(long id) {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    public StudentDto createStudent(CreateStudentCommand command) {
        Student student = new Student(command.getName(), command.getEmail(), command.getGithubUserName(), command.getDetails());
        repository.save(student);
        return mapper.map(student, StudentDto.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Transactional
    public StudentDto updateStudent(long id, UpdateStudentCommand command) {
        Student student = getStudentById(id);
        student.update(command.getName(), command.getEmail(), command.getGithubUserName(), command.getDetails());
        return mapper.map(student, StudentDto.class);
    }

    public StudentDto addRegistration(long id, Registration registration) {
        Student student = getStudentById(id);
        student.addRegistration(registration);
        return mapper.map(student,StudentDto.class);
    }
}
