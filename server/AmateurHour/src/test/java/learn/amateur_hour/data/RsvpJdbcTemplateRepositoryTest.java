
package learn.amateur_hour.data;

import learn.amateur_hour.models.EventCapacity;
import learn.amateur_hour.models.Rsvp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RsvpJdbcTemplateRepositoryTest {

    @Autowired
    RsvpJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void add() {
        Rsvp rsvp = makeRsvp();
        rsvp = repository.add(rsvp);
        assertNotNull(rsvp);
    }

    @Test
    void findByUserEvent() {
        Rsvp rsvp = repository.findByUserEvent(10, 6);
        assertNotNull(rsvp);
    }

    @Test
    void findByUserEventFailsIfNotExists() {
        Rsvp rsvp = repository.findByUserEvent(100, 100);
        assertNull(rsvp);
    }

    @Test
    void findEventsByUser() {
        List<Rsvp> result = repository.findEventsByUser(10);
        assertTrue(result.size() > 0);
    }

    @Test
    void findEventsByUserFailsIfNotExists() {
        List<Rsvp> result = repository.findEventsByUser(100);
        assertFalse(result.size() > 0);
    }

    @Test
    void currentEnrollmentsForEvent() {
        EventCapacity ec = repository.currentEnrollmentsForEvent(6);
        assertNotNull(ec);

        assertEquals(7, ec.getMaxCapacity());
        assertTrue(ec.getCurrentCapacity() >= 3);
    }

    @Test
    void currentEnrollmentsForEventReturnsNoCapacityIfNotExisting() {
        EventCapacity ec = repository.currentEnrollmentsForEvent(666);
        assertEquals(0, ec.getMaxCapacity());
    }

    @Test
    void deleteRsvp() {
        assertTrue(repository.deleteRsvp(15, 1));
        assertFalse(repository.deleteRsvp(15, 1));

        assertNull(repository.findByUserEvent(15, 1));
    }

    private Rsvp makeRsvp() {
        Rsvp rsvp = new Rsvp();
        rsvp.setEventId(6);
        rsvp.setAppUserId(1);

        return rsvp;
    }
}