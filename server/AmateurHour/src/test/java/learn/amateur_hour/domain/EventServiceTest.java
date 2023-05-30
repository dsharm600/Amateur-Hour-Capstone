package learn.amateur_hour.domain;

import learn.amateur_hour.data.EventRepository;
import learn.amateur_hour.models.AppUser;
import learn.amateur_hour.models.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class EventServiceTest {

    @Autowired
    EventService service;

    @MockBean
    EventRepository repository;

    @Test
    void findFutureEventsByState() {
        List<Event> expected = new ArrayList<>();
        Event event1 = makeEvent();
        event1.setEventId(1);
        expected.add(event1);
        Event event2 = makeEvent();
        event2.setEventId(2);
        expected.add(event2);

        when(repository.findFutureEventsByState("WI", 1)).thenReturn(expected);

        List<Event> actual = service.findFutureEventsByState("WI", 1);

        assertEquals(expected, actual);
    }

    @Test
    void findPastEvents() {
        List<Event> expected = new ArrayList<>();
        Event event1 = makeEvent();
        event1.setEventId(1);
        event1.setEventDate(LocalDateTime.now().minusDays(5));
        expected.add(event1);
        Event event2 = makeEvent();
        event2.setEventId(2);
        event2.setEventDate(LocalDateTime.now().minusDays(5));
        expected.add(event2);

        when(repository.findPastEvents(1)).thenReturn(expected);

        List<Event> actual = service.findPastEvents(1);

        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        Event expected = makeEvent();
        expected.setEventId(1);


        when(repository.findById(1)).thenReturn(expected);
        Event actual = service.findById(1);

        assertEquals(expected, actual);
    }

    @Test
    void cancelById() {
        Result<Event> expected = new Result<>();


        when(repository.cancelById(anyInt())).thenReturn(true);
        Result<Event> actual = service.cancelById(1, makeAppUser());

        assertTrue(expected.isSuccess());
    }

    @Test
    void addInvalid() {
        Event event = makeEvent();
        event.setHost("");
        Result<Event> result = service.add(event);
        assertEquals(ResultType.INVALID, result.getType());


        event = makeEvent();
        event.setEventId(5);
        result = service.add(event);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void addValid() {
        Event expected = makeEvent();
        expected.setEventId(1);
        Event arg = makeEvent();

        when(repository.add(arg)).thenReturn(expected);
        Result<Event> result = service.add(arg);
        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void updateInvalid() {
        Event event = makeEvent();

        Result<Event> result = service.update(event, makeAppUser());
        assertEquals(ResultType.INVALID, result.getType());

        event.setEventId(1);
        event.setHost("");

        result = service.update(event, makeAppUser());
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void updateValid() {
        Event event = makeEvent();
        event.setEventId(1);

        when(repository.update(event)).thenReturn(true);
        Result<Event> result = service.update(event, makeAppUser());
        assertEquals(ResultType.SUCCESS, result.getType());
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

    private AppUser makeAppUser() {
        List<String> newRole = new ArrayList<>();
        newRole.add("ADMIN");
        AppUser user = new AppUser(1,"Thenue", "EMPTY BIO","P@ssw0rd!", "newUser@email.com",false, newRole);
        return user;
    }
}