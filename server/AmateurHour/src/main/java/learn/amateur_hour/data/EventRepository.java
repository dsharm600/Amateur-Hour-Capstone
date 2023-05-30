package learn.amateur_hour.data;

import learn.amateur_hour.models.Event;
import learn.amateur_hour.models.Rating;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventRepository {

    List<Event> findFutureEventsByState(String stateCode, int page);

    List<Event> findPastEvents(int page);

    Event findById(int eventId);

    Event add(Event event);

    boolean update(Event event);

    @Transactional
    boolean cancelById(int eventId);
}
