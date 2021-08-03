package mentortools.completion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.lessons.Lesson;
import mentortools.students.Student;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lesson_completions")
public class LessonCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    @Column(name = "video_status")
    private CompletionStatus videoStatus;

    @Column(name = "video_date")
    private LocalDateTime videoDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "exercise_status")
    private CompletionStatus exerciseStatus;

    @Column(name = "exercise_date")
    private LocalDateTime exerciseDate;

    @Column(name = "commit_url")
    private String commitUrl;

    public LessonCompletion(Student student, Lesson lesson) {
        this.student = student;
        this.lesson = lesson;
        videoStatus = CompletionStatus.NOT_COMPLETED;
        exerciseStatus = CompletionStatus.NOT_COMPLETED;
    }

    public void update(UpdateLessonCompletionCommand command){
        if(command.getCommitUrl() != null){
            setCommitUrl(command.getCommitUrl());
        }
        if(command.getExerciseDate() != null){
            setExerciseDate(command.getExerciseDate());
        }
        if(command.getExerciseStatus() != null){
            setExerciseStatus(command.getExerciseStatus());
        }
        if(command.getVideoDate() != null){
            setVideoDate(command.getVideoDate());
        }
        if(command.getVideoStatus() != null){
            setVideoStatus(command.getVideoStatus());
        }
    }
}
