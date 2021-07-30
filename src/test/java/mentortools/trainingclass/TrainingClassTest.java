package mentortools.trainingclass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TrainingClassTest {

    TrainingClass trainingClass;

    @BeforeEach
    void setUp() {
        trainingClass = new TrainingClass("Újratervezés", LocalDateTime.parse("2020-10-15T08:00:00"), LocalDateTime.parse("2021-04-15T08:00:00"));
    }

    @Test
    void testUpdateWithAllValid() {
        trainingClass.update("Struktúra", LocalDateTime.parse("2020-12-15T08:00:00"), LocalDateTime.parse("2021-06-15T08:00:00"));

        assertEquals("Struktúra", trainingClass.getName());
    }

    @Test
    void testUpdateInvalidStartWithNullEnd() {
        assertThrows(InvalidEndTimeException.class, () -> trainingClass.update("Struktúra", LocalDateTime.parse("2022-12-15T08:00:00"), null));
    }

    @Test
    void testUpdateInvalidEndWithNullStart() {
        assertThrows(InvalidEndTimeException.class, () -> trainingClass.update("Struktúra", null, LocalDateTime.parse("2019-06-15T08:00:00")));
    }

    @Test
    void testInvalidName(){
        trainingClass.update("",null,null);

        assertEquals("Újratervezés", trainingClass.getName());
    }
}