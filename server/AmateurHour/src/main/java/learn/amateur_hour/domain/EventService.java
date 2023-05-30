package learn.amateur_hour.domain;

import learn.amateur_hour.App;
import learn.amateur_hour.data.EventRepository;
import learn.amateur_hour.models.AppUser;
import learn.amateur_hour.models.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    public List<Event> findFutureEventsByState(String stateCode, int page) {
        return repository.findFutureEventsByState(stateCode, page);
    }

    public List<Event> findPastEvents(int page) {
        return repository.findPastEvents(page);
    }

    public Event findById(int eventId) {
        return repository.findById(eventId);
    }

    public Result<Event> cancelById(int eventId, AppUser user) {
        Result<Event> result = new Result<>();
        Event hostEvent = repository.findById(eventId);
        if (hostEvent == null) {
            result.addMessage("Event Does not exist", ResultType.INVALID);
            return result;
        }

        if (!(user.getUsername().equals(hostEvent.getHost()) || user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))){
            result.addMessage("Must be an admin or host to delete the event", ResultType.INVALID);
            return result;
        }

        if (!repository.cancelById(eventId)) {
            String msg = String.format("Event Id: %s, not found", eventId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<Event> add(Event event) {
        Result<Event> result = validate(event);
        if (!result.isSuccess())
            return result;

        if (event.getEventId() != 0) {
            result.addMessage("Event id can't be set when adding event", ResultType.INVALID);
            return result;
        }

        event = repository.add(event);

        if (event == null) {
            result.addMessage("Event addition failed", ResultType.INVALID);
            return result;
        }

        result.setPayload(event);
        return result;
    }

    public Result<Event> update(Event event, AppUser user) {
        Result<Event> result = validate(event);
        if (!result.isSuccess())
            return result;

        if (event.getEventId() <= 0) {
            result.addMessage("Event id must be identified in order to update", ResultType.INVALID);
            return result;
        }

        if (event.getTags().size() > 0) {
            for (int i = 0; i < event.getTags().size(); i++) {
                for (int j = i+1; j < event.getTags().size(); j++) {
                    if (event.getTags().get(i).getTagId() == (event.getTags().get(j).getTagId())) {
                        result.addMessage("You cannot have duplicate tags", ResultType.INVALID);
                        return result;
                    }
                }
            }
        }

        if (!(user.getUsername().equals(event.getHost()) || user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))){
            result.addMessage("Must be an admin or host to edit the event", ResultType.INVALID);
            return result;
        }


        if (!repository.update(event)) {
            String msg = String.format("Event Id: %s, not found", event.getEventId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }



    private Result<Event> validate(Event event) {
        Result<Event> result = new Result<>();
        if (event == null) {
            result.addMessage("Event cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(event.getHost())) {
            result.addMessage("Host cannot be null", ResultType.INVALID);
        }

        if (event.getCapacity() <= 3) {
            result.addMessage("Events must support at least 3 additional members", ResultType.INVALID);
        }

        if (event.getEventDate() == null) {
            result.addMessage("Date cannot be null", ResultType.INVALID);
            return result;
        }

        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            result.addMessage("Date cannot be in the past", ResultType.INVALID);
        }

        if (event.getTags().size() > 3) {
            result.addMessage("You have too many tags attached. Please limit to 3 or less", ResultType.INVALID);
        }

        return result;
    }
}
