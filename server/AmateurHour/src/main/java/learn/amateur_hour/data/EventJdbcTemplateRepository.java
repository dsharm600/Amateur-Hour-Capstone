package learn.amateur_hour.data;

import learn.amateur_hour.data.mappers.EventMapper;
import learn.amateur_hour.data.mappers.RatingMapper;
import learn.amateur_hour.data.mappers.TagMapper;
import learn.amateur_hour.models.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventJdbcTemplateRepository implements EventRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Event> findFutureEventsByState(String stateCode, int page) {
        final int displayPage = (page-1)*20;
        final String sql = "SELECT event_id, username, event_title, event_capacity, event_address, city_name, state_code, event_date, event_description, event_notes, cancelled" +
                " FROM app_event e" +
                " inner join app_user u" +
                " on e.host_id = u.app_user_id" +
                " inner join state s" +
                " on s.state_id = e.state_id" +
                " inner join city c" +
                " on c.city_id = e.city_id" +
                " where event_date >= now() and state_code = ? limit ? , 20";
        return jdbcTemplate.query(sql, new EventMapper(), stateCode, displayPage);
    }

    @Override
    public List<Event> findPastEvents(int page) {
        final int displayPage = (page-1)*20;
        final String sql = "SELECT event_id, username, event_title, event_capacity, event_address, city_name, state_code, event_date, event_description, event_notes, cancelled" +
                " FROM app_event e" +
                " inner join app_user u" +
                " on e.host_id = u.app_user_id" +
                " inner join state s" +
                " on s.state_id = e.state_id" +
                " inner join city c" +
                " on c.city_id = e.city_id" +
                " where event_date <= now() limit ? , 20";
        return jdbcTemplate.query(sql, new EventMapper(), displayPage);
    }

    @Override
    public Event findById(int eventId) {
        final String sql = "SELECT event_id, username, event_title, event_capacity, event_address, city_name, state_code, event_date, event_description, event_notes, cancelled" +
                " FROM app_event e" +
                " inner join app_user u" +
                " on e.host_id = u.app_user_id" +
                " inner join state s" +
                " on s.state_id = e.state_id" +
                " inner join city c" +
                " on c.city_id = e.city_id" +
                " where event_id = ?";

        Event event = jdbcTemplate.query(sql, new EventMapper(),eventId).stream().findFirst().orElse(null);

        if (event != null) {
            insertTags(event);
            insertRatings(event);
        }

        return event;
    }


    @Override
    public Event add(Event event) {
        final String sql = "insert into app_event (host_id, event_description, event_notes, event_address, city_id, state_id, event_date, event_title, event_capacity)" +
                "values ((select app_user_id from app_user where username = ?), ?, ?, ?, (select city_id from city where city_name = ?), (select state_id from state where state_code = ?), ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getHost());
            ps.setString(2, event.getEventDescription());
            ps.setString(3, event.getEventNotes());
            ps.setString(4, event.getEventAddress());
            ps.setString(5, event.getCity());
            ps.setString(6, event.getState());
            ps.setTimestamp(7, Timestamp.valueOf(event.getEventDate()));
            ps.setString(8, event.getTitle());
            ps.setInt(9, event.getCapacity());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0)
            return null;

        event.setEventId(keyHolder.getKey().intValue());

        addEventTags(event);

        return event;
    }

    @Override
    public boolean update(Event event) {
        final String sql = "update app_event set host_id = (select app_user_id from app_user where username = ?)," +
                " event_description = ?," +
                " event_notes = ?," +
                " event_address = ?," +
                " city_id = (select city_id from city where city_name = ?)," +
                " state_id = (select state_id from state where state_code = ?)," +
                " event_date = ?," +
                " event_title = ?," +
                " event_capacity = ?" +
                " where event_id = ?";

        boolean result = jdbcTemplate.update(sql,
                event.getHost(),
                event.getEventDescription(),
                event.getEventNotes(),
                event.getEventAddress(),
                event.getCity(),
                event.getState(),
                event.getEventDate(),
                event.getTitle(),
                event.getCapacity(),
                event.getEventId()) > 0;

        if (clearEventTags(event) > 0)
            result = true;


        int tagsAdded = addEventTags(event);
        if (tagsAdded > 0 && !result) {
            result = true;
        }

        return result;
    }

    @Override
    public boolean cancelById(int eventId) {
        final String sql = "update app_event set cancelled = 1 where event_id = ?";

        return jdbcTemplate.update(sql, eventId) > 0;
    }

    private void insertTags(Event event) {
        final String sql = "select t.tag_id, tag_name from tag t join event_tag e on t.tag_id = e.tag_id where event_id = ?";

        var tags = jdbcTemplate.query(sql, new TagMapper(), event.getEventId());
        event.setTags(tags);
    }

    private void insertRatings(Event event) {
        final String sql = "select rating_id, r.app_user_id, username, event_id, rating_comment, rating_score " +
                "from rating r inner join app_user u on r.app_user_id = u.app_user_id " +
                "where event_id = ?";

        var ratings = jdbcTemplate.query(sql, new RatingMapper(), event.getEventId());
        event.setRatings(ratings);
    }

    private int addEventTags(Event event) {
        final String sql = "insert into event_tag (tag_id, event_id) values (?, ?)";
        int rowsAffected = 0;
        for (int i = 0; i < event.getTags().size(); i++) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int j = i;
            rowsAffected += jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, event.getTags().get(j).getTagId());
                ps.setInt(2, event.getEventId());
                return ps;
            }, keyHolder);
        }

        return rowsAffected;
    }

    private int clearEventTags(Event event) {
        final String sql = "delete from event_tag where event_id = ? and tag_id = ?";
        int rowsAffected = 0;

        for (int i = 0; i < event.getTags().size(); i++) {
            rowsAffected += jdbcTemplate.update(sql, event.getEventId(), event.getTags().get(i).getTagId());
        }

        return rowsAffected;
    }
}
