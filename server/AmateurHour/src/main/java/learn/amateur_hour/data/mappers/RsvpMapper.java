package learn.amateur_hour.data.mappers;

import learn.amateur_hour.models.Rsvp;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RsvpMapper implements RowMapper<Rsvp> {
    @Override
    public Rsvp mapRow(ResultSet resultSet, int i) throws SQLException {
        Rsvp rsvp = new Rsvp();
        rsvp.setAppUserId(resultSet.getInt("app_user_id"));
        rsvp.setEventId(resultSet.getInt("event_id"));

        return rsvp;
    }
}
