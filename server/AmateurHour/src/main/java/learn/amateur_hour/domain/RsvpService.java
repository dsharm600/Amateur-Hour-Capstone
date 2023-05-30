package learn.amateur_hour.domain;

import learn.amateur_hour.data.EventRepository;
import learn.amateur_hour.data.RsvpRepository;
import learn.amateur_hour.models.EventCapacity;
import learn.amateur_hour.models.Rsvp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RsvpService {

    private final RsvpRepository repository;

    public Result<Rsvp> add(Rsvp rsvp) {
        Result<Rsvp> result = validate(rsvp);
        if (!result.isSuccess())
            return result;

        rsvp = repository.add(rsvp);

        if (rsvp == null) {
            result.addMessage("Rsvp addition failed", ResultType.INVALID);
            return result;
        }

        result.setPayload(rsvp);
        return result;
    }

    public EventCapacity currentEnrollmentsForEvent(int eventId) {
        return repository.currentEnrollmentsForEvent(eventId);
    }

    public Rsvp findByUserEvent(int userId, int eventId) {
        return repository.findByUserEvent(userId, eventId);
    }

    public List<Rsvp> findEventsByUser(int userId) {
        return repository.findEventsByUser(userId);
    }

    public boolean deleteRsvp(int appUserId, int eventId) {
        return repository.deleteRsvp(appUserId, eventId);
    }

    private Result<Rsvp> validate(Rsvp rsvp) {
        Result<Rsvp> result = new Result<>();
        if (rsvp == null) {
            result.addMessage("Rsvp cannot be null", ResultType.INVALID);
            return result;
        }

        if (rsvp.getAppUserId() == repository.getHostIdFromEvent(rsvp.getEventId())) {
            result.addMessage("Can't rsvp to an event you're hosting", ResultType.INVALID);
        }

        if (rsvp.getAppUserId() <= 0) {
            result.addMessage("appUserId must be greater than 0", ResultType.INVALID);
        }

        if (rsvp.getEventId() <= 0) {
            result.addMessage("eventId must be greater than 0", ResultType.INVALID);
        }

        if (repository.findByUserEvent(rsvp.getAppUserId(), rsvp.getEventId()) != null) {
            result.addMessage("You cannot join the same event twice", ResultType.INVALID);
        }

        EventCapacity ec = repository.currentEnrollmentsForEvent(rsvp.getEventId());

        if (ec == null) {
            result.addMessage("This event does not exist", ResultType.INVALID);
            return result;
        }

        if (ec.getCurrentCapacity() >= ec.getMaxCapacity()) {
            String message = "You cannot join this event because it is over its predetermined capacity ("+ec.getMaxCapacity()+")";
            result.addMessage(message, ResultType.INVALID);
        }

        return result;
    }
}
