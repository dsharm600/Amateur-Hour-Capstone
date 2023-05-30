package learn.amateur_hour.data;

import learn.amateur_hour.models.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RatingJdbcTemplateRepositoryTest {

    @Autowired
    RatingJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void add() {
        Rating rating = makeRating();

        rating = repository.add(rating);
        assertNotNull(rating);

        assertTrue(rating.getRatingId() > 0);
    }

    @Test
    void findById() {
        Rating rating = repository.findById(1);

        assertNotNull(rating);
        assertEquals(10, rating.getAppUserId());
    }

    @Test
    void findByIdFailsWhenInvalid() {
        Rating rating = repository.findById(100);

        assertNull(rating);
    }

    @Test
    void findAllByEventId() {
        List<Rating> ratings = repository.findAllByEventId(1);

        assertTrue(ratings.size() > 0);
    }

    @Test
    void findAllByUserId() {
        List<Rating> ratings = repository.findAllByUserId(12);

        assertTrue(ratings.size() > 0);
    }

    @Test
    void updateWorksWhenValid() {
        Rating rating = makeRating();
        rating.setRatingId(1);
        rating.setAppUserId(10);
        rating.setUsername("Onancery");
        rating.setEventId(1);

        assertTrue(repository.update(rating));
        assertEquals("5/7", repository.findById(1).getComment());
    }

    @Test
    void deleteByIdWorksWhenExists() {
        assertTrue(repository.deleteById(8));
        assertFalse(repository.deleteById(8));

        assertNull(repository.findById(8));
    }

    @Test
    void deleteFailsWhenNotExists() {
        assertFalse(repository.deleteById(100));
    }

    private Rating makeRating() {
        Rating rating = new Rating();

        rating.setRatingId(0);
        rating.setAppUserId(1);
        rating.setUsername("Thenue");
        rating.setEventId(10);
        rating.setComment("5/7");
        rating.setScore(5);

        return rating;
    }
}