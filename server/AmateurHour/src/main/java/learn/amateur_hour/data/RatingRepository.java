package learn.amateur_hour.data;

import learn.amateur_hour.models.Rating;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RatingRepository {
    Rating add(Rating rating);

    Rating findById(int ratingId);

    List<Rating> findAllByEventId(int eventId);

    List<Rating> findAllByUserId(int eventId);

    boolean update(Rating rating);

    @Transactional
    boolean deleteById(int ratingId);
}
