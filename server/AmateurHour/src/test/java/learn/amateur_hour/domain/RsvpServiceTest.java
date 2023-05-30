package learn.amateur_hour.domain;

import learn.amateur_hour.data.RsvpRepository;
import learn.amateur_hour.models.EventCapacity;
import learn.amateur_hour.models.Rsvp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class RsvpServiceTest {

    @Autowired
    RsvpService service;

    @MockBean
    RsvpRepository repository;


    @Test
    void currentEnrollmentsForEvent() {
        //Just a passthrough
        EventCapacity ec = new EventCapacity();
        ec.setCurrentCapacity(1);
        ec.setMaxCapacity(5);

        when(repository.currentEnrollmentsForEvent(anyInt())).thenReturn(ec);
        EventCapacity ec2 = service.currentEnrollmentsForEvent(1);
        assertEquals(ec, ec2);
    }

    @Test
    void findByUserEvent() {
        Rsvp rsvp = makeRsvp();

        when(repository.findByUserEvent(anyInt(), anyInt())).thenReturn(rsvp);
        assertEquals(rsvp, service.findByUserEvent(1, 1));
    }

    @Test
    void findEventsByUser() {
        Rsvp rsvp1 = makeRsvp();
        Rsvp rsvp2 = makeRsvp();
        rsvp2.setAppUserId(2);
        List<Rsvp> events = List.of(rsvp1, rsvp2);

        when(repository.findEventsByUser(anyInt())).thenReturn(events);
        assertEquals(events, service.findEventsByUser(1));
    }

    @Test
    void addWorksWhenValid() {
        Rsvp rsvp = makeRsvp();
        Result<Rsvp> result = new Result<>();
        result.setPayload(rsvp);
        EventCapacity ec = new EventCapacity();
        ec.setMaxCapacity(5);
        ec.setCurrentCapacity(2);

        when(repository.add(any())).thenReturn(rsvp);
        when(repository.currentEnrollmentsForEvent(anyInt())).thenReturn(ec);
        assertTrue(service.add(rsvp).isSuccess());
    }

    @Test
    void addFailsWhenInvalid() {
        Result<Rsvp> result = new Result<>();
        result.addMessage("Rsvp cannot be null", ResultType.INVALID);


        Rsvp rsvp = null;
        assertEquals(result.getMessages(), service.add(rsvp).getMessages());
    }

    @Test
    void deleteByIdDeletesWhenExists() {
        //passthrough, no logic
        when(repository.deleteRsvp(anyInt(), anyInt())).thenReturn(true);
        assertTrue(service.deleteRsvp(1, 1));
    }

    @Test
    void deleteByIdsFailsWhenNotExists() {
        when(repository.deleteRsvp(anyInt(), anyInt())).thenReturn(false);
        assertFalse(service.deleteRsvp(1111, 11111));
    }

    private Rsvp makeRsvp() {
        Rsvp rsvp = new Rsvp();
        rsvp.setAppUserId(1);
        rsvp.setEventId(1);

        return rsvp;
    }
}
