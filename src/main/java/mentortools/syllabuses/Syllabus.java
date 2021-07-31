package mentortools.syllabuses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.modules.Module;
import mentortools.trainingclass.TrainingClass;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "syllabuses")
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "syllabus")
    private List<TrainingClass> trainingClasses;

    public void addClass(TrainingClass trainingClass) {
        if (trainingClasses == null) {
            trainingClasses = new ArrayList<>();
        }
        trainingClasses.add(trainingClass);
        trainingClass.setSyllabus(this);
    }

    @ManyToMany
    @JoinTable(name = "SYL_MOD",
    joinColumns = @JoinColumn(name = "syl_id"),
    inverseJoinColumns = @JoinColumn(name = "mod_id"))
    public List<Module> modules;

    public Syllabus(String name) {
        this.name = name;
    }

    public void addModule(Module module) {
        if(modules == null){
            modules = new ArrayList<>();
        }

        modules.add(module);
    }
}
