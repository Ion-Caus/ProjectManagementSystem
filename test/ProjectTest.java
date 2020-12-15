import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project("Test","Created", LocalDate.now(), LocalDate.now(), new Team());
    }

    @Test
    void setName() {
        //set name to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> project.setName(null));
        assertEquals("Name cannot be empty", exception.getMessage(), "Name set to null");

        //set name to white space
        exception = assertThrows(IllegalArgumentException.class, () -> project.setName("  "));
        assertEquals("Name cannot be empty", exception.getMessage(), "Name set to white space");
    }

    @Test
    void setStatus() {
        //set status to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> project.setStatus(null));
        assertEquals("Illegal status given", exception.getMessage(), "Status set to null");

        //set status to Created, In Process, Waiting For Approval, Finished
        assertDoesNotThrow(() -> project.setStatus("Created"));
        assertDoesNotThrow(() -> project.setStatus("In Process"));
        assertDoesNotThrow(() -> project.setStatus("Waiting For Approval"));
        assertDoesNotThrow(() -> project.setStatus("Finished"));

        //set status to anything else
        exception = assertThrows(IllegalArgumentException.class, () -> project.setStatus("InProcess"));
        assertEquals("Illegal status given", exception.getMessage(), "Invalid status set");
    }

    @Test
    void setDeadline() {
        //set deadline to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> project.setDeadline(null));
        assertEquals("Null deadline given", exception.getMessage(), "Deadline set to null");
    }

    @Test
    void setEstimate() {
        //set estimate to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> project.setEstimate(null));
        assertEquals("Null estimate given", exception.getMessage(), "Estimate set to null");
    }

    @Test
    void setTeam() {
        //set team to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> project.setTeam(null));
        assertEquals("Null team given", exception.getMessage(), "Team set to null");

        // set team
        assertDoesNotThrow(() -> project.setTeam(new Team()));


    }
}