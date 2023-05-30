package learn.amateur_hour.data.mappers;

import learn.amateur_hour.models.Event;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
        Event event = new Event();
        event.setEventId(resultSet.getInt("event_id"));
        event.setHost(resultSet.getString("username"));
        event.setEventDescription(resultSet.getString("event_description"));
        event.setEventNotes(resultSet.getString("event_notes"));
        event.setEventAddress(resultSet.getString("event_address"));
        event.setCity(resultSet.getString("city_name"));
        event.setState(resultSet.getString("state_code"));
        event.setEventDate(resultSet.getTimestamp("event_date").toLocalDateTime());
        event.setTitle(resultSet.getString("event_title"));
        event.setCapacity(resultSet.getInt("event_capacity"));
        event.setCancelled(resultSet.getBoolean("cancelled"));
        return event;
    }
}
