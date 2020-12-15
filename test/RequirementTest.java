import model.Requirement;
import model.TeamMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RequirementTest {
    private Requirement requirement;

    @BeforeEach
    void setUp() {
        requirement = new Requirement("Test", "Not Started", "Functional", "Testing", LocalDate.now(), LocalDate.now(), new TeamMember("Test"));
    }

    @Test
    void setTitle() {
        //set title to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requirement.setTitle(null));
        assertEquals("Title cannot be empty", exception.getMessage(), "Title set to null");

        //set title to white space
        exception = assertThrows(IllegalArgumentException.class, () -> requirement.setTitle("  "));
        assertEquals("Title cannot be empty", exception.getMessage(), "Title set to white space");
    }

    @Test
    void setStatus() {
        //set status to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requirement.setStatus(null));
        assertEquals("Illegal status given", exception.getMessage(), "Status set to null");

        //set status to Not Started, In Process, Waiting For Approval, Approved, Rejected
        assertDoesNotThrow(() -> requirement.setStatus("Not Started"));
        assertDoesNotThrow(() -> requirement.setStatus("In Process"));
        assertDoesNotThrow(() -> requirement.setStatus("Waiting For Approval"));
        assertDoesNotThrow(() -> requirement.setStatus("Approved"));
        assertDoesNotThrow(() -> requirement.setStatus("Rejected"));

        //set status to anything else
        exception = assertThrows(IllegalArgumentException.class, () -> requirement.setStatus("InProcess"));
        assertEquals("Illegal status given", exception.getMessage(), "Invalid status set");
    }

    @Test
    void setType() {
        //set type to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requirement.setType(null));
        assertEquals("Illegal type given", exception.getMessage(), "Type set to null");

        //set status to Functional, Non-functional, Project Related
        //assertDoesNotThrow(() -> requirement.setType("Functional"));
        assertDoesNotThrow(() -> requirement.setType("Non-functional"));
        assertDoesNotThrow(() -> requirement.setType("Project Related"));

        //set status to anything else
        exception = assertThrows(IllegalArgumentException.class, () -> requirement.setType("Non functional"));
        assertEquals("Illegal type given", exception.getMessage(), "Invalid type set");
    }

    @Test
    void setDescription() {
        //set description to null
        requirement.setDescription(null);
        assertEquals("", requirement.getDescription());

        //set description to anything
        requirement.setDescription("  ");
        assertEquals("  ", requirement.getDescription());

    }

    @Test
    void setDeadline() {
        //set deadline to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requirement.setDeadline(null));
        assertEquals("Null deadline given", exception.getMessage(), "Deadline set to null");
    }

    @Test
    void setEstimate() {
        //set estimate to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requirement.setEstimate(null));
        assertEquals("Null estimate given", exception.getMessage(), "Estimate set to null");
    }

    @Test
    void setResponsibleTeamMember() {
        //set responsible team member to null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requirement.setResponsibleTeamMember(null));
        assertEquals("Null responsible team member given", exception.getMessage(), "ResponsibleTeamMember set to null");

        assertDoesNotThrow(() -> requirement.setResponsibleTeamMember(new TeamMember("Ion")));
    }
}