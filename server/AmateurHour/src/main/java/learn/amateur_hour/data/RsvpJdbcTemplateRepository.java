package learn.amateur_hour.data;

import learn.amateur_hour.data.mappers.CapacityMapper;
import learn.amateur_hour.data.mappers.RsvpMapper;
import learn.amateur_hour.models.EventCapacity;
import learn.amateur_hour.models.Rsvp;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RsvpJdbcTemplateRepository implements RsvpRepository {

    private final JdbcTemplate jdbcTemplate;

    public RsvpJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Rsvp add(Rsvp rsvp) {
        final String sql = "insert into rsvp (app_user_id, event_id) values (?, ?);";

        int rowsAffected = jdbcTemplate.update(sql,rsvp.getAppUserId(), rsvp.getEventId() );

        if (rowsAffected <= 0) {
            return null;
        }

        return rsvp;
    }

    @Override
    public int getHostIdFromEvent(int eventId) {
        final String sql = "select host_id from app_event where event_id = ?";

        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, eventId);

        if (result == null) {
            return 0;
        }

        return result;
    }

    @Override
    public Rsvp findByUserEvent(int userId, int eventId) {
        final String sql = "select app_user_id, event_id from rsvp " +
                "where app_user_id = ? and event_id = ?";

        return jdbcTemplate.query(sql, new RsvpMapper(), userId, eventId).stream().findFirst().orElse(null);
    }

    @Override
    public List<Rsvp> findEventsByUser(int userId) {
        final String sql = "select app_user_id, event_id from rsvp " +
                "where app_user_id = ?";

        return jdbcTemplate.query(sql, new RsvpMapper(), userId);
    }

    @Override
    public EventCapacity currentEnrollmentsForEvent(int eventId) {
        final String sql = "select count(app_user_id) as 'current_capacity', event_capacity as 'max_capacity' " +
                "from app_event e left join rsvp r on e.event_id = r.event_id " +
                "where e.event_id = ?";
        return jdbcTemplate.query(sql, new CapacityMapper(), eventId).stream().findFirst().orElse(null);
    }

    @Override
    public boolean deleteRsvp(int appUserId, int eventId) {
        return jdbcTemplate.update("delete from rsvp where app_user_id = ? and event_id =?", appUserId, eventId) > 0;
    }
}
