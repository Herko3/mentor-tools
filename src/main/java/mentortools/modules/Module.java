package mentortools.modules;

import lombok.*;
import mentortools.lessons.Lesson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE},mappedBy = "module")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Lesson> lessons;

    public Module(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public void addLesson(Lesson lesson){
        if(lessons == null){
            lessons = new ArrayList<>();
        }
        lessons.add(lesson);
        lesson.setModule(this);
    }
}
