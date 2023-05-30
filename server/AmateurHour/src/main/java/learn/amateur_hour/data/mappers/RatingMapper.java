package learn.amateur_hour.data.mappers;

import learn.amateur_hour.models.Rating;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingMapper implements RowMapper<Rating> {
    @Override
    public Rating mapRow(ResultSet resultSet, int i) throws SQLException {
        Rating rating = new Rating();
        rating.setRatingId(resultSet.getInt("rating_id"));
        rating.setAppUserId(resultSet.getInt("app_user_id"));
        rating.setUsername(resultSet.getString("username"));
        rating.setEventId(resultSet.getInt("event_id"));
        rating.setComment(resultSet.getString("rating_comment"));
        rating.setScore(resultSet.getInt("rating_score"));
        return rating;
    }
}
