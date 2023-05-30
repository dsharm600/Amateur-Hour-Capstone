package learn.amateur_hour.data;

import learn.amateur_hour.data.mappers.RatingMapper;
import learn.amateur_hour.models.Rating;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RatingJdbcTemplateRepository implements RatingRepository {

    private final JdbcTemplate jdbcTemplate;

    public RatingJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // NOTE: CREATE new Rating

    @Override
    public Rating add(Rating rating) {

        final String sql = "insert into rating (app_user_id, event_id, rating_comment, rating_score) " +
                "values ((select app_user_id from app_user where username = ?),?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rating.getUsername());
            ps.setInt(2, rating.getEventId());
            ps.setString(3, rating.getComment());
            ps.setInt(4, rating.getScore());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        rating.setRatingId(keyHolder.getKey().intValue());
        return rating;
    }

    // NOTE: READ Rating
    @Override
    public Rating findById(int ratingId) {
        final String sql = "select rating_id, username, r.app_user_id as app_user_id, event_id, rating_comment, rating_score from rating r" +
                " inner join app_user u" +
                " on r.app_user_id = u.app_user_id" +
                " where rating_id = ?";

        return jdbcTemplate.query(sql, new RatingMapper(), ratingId).stream().findFirst().orElse(null);
    }

    @Override
    public List<Rating> findAllByEventId(int eventId){
        final String sql = "select rating_id, username, r.app_user_id as app_user_id, event_id, rating_comment, rating_score from rating r" +
                " inner join app_user u" +
                " on r.app_user_id = u.app_user_id" +
                " where event_id = ?";

        return jdbcTemplate.query(sql, new RatingMapper(), eventId);
    }

    @Override
    public List<Rating> findAllByUserId(int userId){
        final String sql = "select rating_id, username, r.app_user_id as app_user_id, event_id, rating_comment, rating_score from rating r" +
                " inner join app_user u" +
                " on r.app_user_id = u.app_user_id" +
                " where r.app_user_id = ?";

        return jdbcTemplate.query(sql, new RatingMapper(), userId);
    }

    // NOTE: UPDATE Rating
    @Override
    public boolean update(Rating rating) {

        final String sql = "update rating set" +
                " rating_comment = ?," +
                " rating_score = ?" +
                " where rating_id = ?";

        return jdbcTemplate.update(sql,
                rating.getComment(),
                rating.getScore(),
                rating.getRatingId()) > 0;
    }

    // NOTE: DELETE Rating
    @Override
    @Transactional
    public boolean deleteById(int ratingId) {
        return jdbcTemplate.update("delete from rating where rating_id = ?;", ratingId) > 0;

    }

}
