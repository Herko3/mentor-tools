package mentortools.trainingclass;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingClassRepository extends JpaRepository<TrainingClass,Long> {

    List<TrainingClass> findAllBySyllabus_Id(long id);
}
