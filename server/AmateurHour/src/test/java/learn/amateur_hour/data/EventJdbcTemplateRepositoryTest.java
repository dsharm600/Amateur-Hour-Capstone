package learn.amateur_hour.data;

import learn.amateur_hour.models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventJdbcTemplateRepositoryTest {

    @Autowired
    EventJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findFutureEventsByState() {
        List<Event> events = repository.findFutureEventsByState("MN", 1);
        assertNotNull(events);

        assertTrue(events.size() >= 10);
    }

    @Test
    void findPastEvents() {
        List<Event> events = repository.findPastEvents(1);
        assertNotNull(events);

        assertTrue(events.size() >= 1);
    }

    @Test
    void findById() {
        Event event = repository.findById(1);
        assertNotNull(event);

        assertEquals("Lego Building Club", event.getTitle());
    }

    @Test
    void add() {
        Event event = makeEvent();
        event = repository.add(event);
        assertNotNull(event);
        assertTrue(event.getEventId() > 0);
    }

    @Test
    void update() {
        Event event = makeEvent();
        event.setEventId(2);
        assertTrue(repository.update(event));

        event.setEventId(50);
        assertFalse(repository.update(event));
    }

    @Test
    void cancelById() {
        assertTrue(repository.cancelById(10));
        assertTrue(repository.findById(10).isCancelled());
    }

    private Event makeEvent() {
        Event event = new Event();
        event.setEventId(0);
        event.setHost("AmyCooks");
        event.setEventDescription("Test");
        event.setEventNotes("ya");
        event.setEventAddress("aa");
        event.setCity("Milwaukee");
        event.setState("WI");
        event.setEventDate(LocalDateTime.now().plusDays(7));
        event.setTitle("Testing");
        event.setCapacity(10);

        return event;
    }
}