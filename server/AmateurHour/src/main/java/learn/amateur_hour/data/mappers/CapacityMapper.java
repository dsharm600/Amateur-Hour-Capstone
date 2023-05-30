package learn.amateur_hour.data.mappers;

import learn.amateur_hour.models.EventCapacity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CapacityMapper implements RowMapper<EventCapacity> {

    @Override
    public EventCapacity mapRow(ResultSet rs, int i) throws SQLException {
        EventCapacity ec = new EventCapacity();
        ec.setCurrentCapacity(rs.getInt("current_capacity"));
        ec.setMaxCapacity(rs.getInt("max_capacity"));

        return ec;
    }
}
