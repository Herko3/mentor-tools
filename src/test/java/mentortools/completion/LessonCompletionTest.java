package mentortools.completion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LessonCompletionTest {

    LessonCompletion completion;

    @BeforeEach
    void init(){
        completion = new LessonCompletion(1L,null,null,CompletionStatus.NOT_COMPLETED,
                LocalDateTime.of(2020,10,10,10,10),CompletionStatus.NOT_COMPLETED,
                LocalDateTime.of(2021,10,12,10,10),"github.com/asd");
    }

    @Test
    void testUpdateVideoStatus(){
        completion.update(new UpdateLessonCompletionCommand(null,CompletionStatus.COMPLETED,null,
                null,null,null));

        assertEquals(CompletionStatus.COMPLETED,completion.getVideoStatus());

    }

    @Test
    void testUpdateVideoDate(){
        completion.update(new UpdateLessonCompletionCommand(null,null,LocalDateTime.of(2021,5,5,10,10),
                null,null,null));

        assertEquals(LocalDateTime.of(2021,5,5,10,10),completion.getVideoDate());
    }

    @Test
    void testUpdateExerciseStatus(){
        completion.update(new UpdateLessonCompletionCommand(null,null,null,
                CompletionStatus.COMPLETED,null,null));
        assertEquals(CompletionStatus.COMPLETED,completion.getExerciseStatus());
    }

    @Test
    void testUpdateExerciseDate(){
        completion.update(new UpdateLessonCompletionCommand(null,CompletionStatus.COMPLETED,null,
                null,LocalDateTime.of(2021,5,5,10,10),null));
        assertEquals(LocalDateTime.of(2021,5,5,10,10),completion.getExerciseDate());
    }

    @Test
    void testCommitUrl(){
        completion.update(new UpdateLessonCompletionCommand(null,null,null,
                null,null,"github.com"));

        assertEquals("github.com",completion.getCommitUrl());
    }
}