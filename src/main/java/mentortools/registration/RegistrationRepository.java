package mentortools.registration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration,Long> {

    List<Registration> findRegistrationsByTrainingClass_Id(long id);

    List<Registration> findRegistrationsByStudent_Id(long id);

    Registration findRegistrationByTrainingClass_IdAndStudent_Id(long classId,long studentId);
}
