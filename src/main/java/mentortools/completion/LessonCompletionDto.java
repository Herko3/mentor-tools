package mentortools.completion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.lessons.LessonDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonCompletionDto {

    private long id;
    private LessonDto lesson;
    private CompletionStatus videoStatus;
    private LocalDateTime videoDate;
    private CompletionStatus exerciseStatus;
    private LocalDateTime exerciseDate;
    private String commitUrl;
}
