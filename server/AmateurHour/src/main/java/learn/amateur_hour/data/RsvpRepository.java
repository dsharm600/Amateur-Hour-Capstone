package learn.amateur_hour.data;

import learn.amateur_hour.models.EventCapacity;
import learn.amateur_hour.models.Rsvp;

import java.util.List;

public interface RsvpRepository {

    Rsvp add(Rsvp rsvp);

    Rsvp findByUserEvent(int userId, int eventId);

    List<Rsvp> findEventsByUser(int userId);

    int getHostIdFromEvent(int eventId);

    EventCapacity currentEnrollmentsForEvent(int eventId);

    boolean deleteRsvp(int appUserId, int eventId);
}
