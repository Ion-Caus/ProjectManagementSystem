import model.Task;
import model.TeamMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("Test", "Not Started", "Testing", LocalDate.now(), LocalDate.now(), new TeamMember("Test"));
    }

    @Test
    void setTitle() {
        //set title to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> task.setTitle(null));
        assertEquals("Title cannot be empty", exception.getMessage(), "Title set to null");

        //set title to white space
        exception = assertThrows(IllegalArgumentException.class, () -> task.setTitle("  "));
        assertEquals("Title cannot be empty", exception.getMessage(), "Title set to white space");
    }

    @Test
    void setStatus() {
        //set status to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> task.setStatus(null));
        assertEquals("Illegal status given", exception.getMessage(), "Status set to null");

        //set status to Not Started, In Process, Completed
        assertDoesNotThrow(() -> task.setStatus("Not Started"));
        assertDoesNotThrow(() -> task.setStatus("In Process"));
        assertDoesNotThrow(() -> task.setStatus("Completed"));

        //set status to anything else
        exception = assertThrows(IllegalArgumentException.class, () -> task.setStatus("InProcess"));
        assertEquals("Illegal status given", exception.getMessage(), "Invalid status set");
    }

    @Test
    void setDescription() {
        //set description to null
        task.setDescription(null);
        assertEquals("", task.getDescription());

        //set description to anything
        task.setDescription("  ");
        assertEquals("  ", task.getDescription());

    }

    @Test
    void setDeadline() {
        //set deadline to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> task.setDeadline(null));
        assertEquals("Null deadline given", exception.getMessage(), "Deadline set to null");
    }

    @Test
    void setEstimate() {
        //set estimate to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> task.setEstimate(null));
        assertEquals("Null estimate given", exception.getMessage(), "Estimate set to null");
    }

    @Test
    void setResponsibleTeamMember() {
        //set responsible team member to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> task.setResponsibleTeamMember(null));
        assertEquals("Null responsible team member given", exception.getMessage(), "ResponsibleTeamMember set to null");

        assertDoesNotThrow(() -> task.setResponsibleTeamMember(new TeamMember("Ion")));
    }
}
