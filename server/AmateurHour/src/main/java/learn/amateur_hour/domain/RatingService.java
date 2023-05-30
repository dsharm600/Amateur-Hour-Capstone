package learn.amateur_hour.domain;

import learn.amateur_hour.data.RatingRepository;
import learn.amateur_hour.models.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository repository;

    public List<Rating> findAllByUserId(int userId) {
        return repository.findAllByUserId(userId);
    }

    public List<Rating> findAllByEventId(int eventId) {
        return repository.findAllByEventId(eventId);
    }

    public Rating findById(int ratingId) {
        return repository.findById(ratingId);
    }

    public Result<Rating> add(Rating rating) {
        Result<Rating> result = validate(rating);

        if (!result.isSuccess())
            return result;

        if (rating.getRatingId() != 0) {
            result.addMessage("Rating Id shouldn't be set when adding a rating", ResultType.INVALID);
            return result;
        }

        rating = repository.add(rating);

        if (rating == null) {
            result.addMessage("Something went wrong when adding the rating", ResultType.INVALID);
            return result;
        }

        result.setPayload(rating);
        return result;
    }

    public Result<Rating> update(Rating rating) {
        Result<Rating> result = validate(rating);

        if (!result.isSuccess())
            return result;

        if (rating.getRatingId() <= 0) {
            result.addMessage("A valid id must be provided to update a rating", ResultType.INVALID);
            return result;
        }

        if (!repository.update(rating)) {
            String msg = String.format("Rating Id: %s, not found", rating.getRatingId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int ratingId) {
        return repository.deleteById(ratingId);
    }

    private Result<Rating> validate(Rating rating) {
        Result<Rating> result = new Result<>();

        if (rating == null) {
            result.addMessage("Rating cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(rating.getUsername())) {
            result.addMessage("Username cannot be blank", ResultType.INVALID);
        }

        if (rating.getScore() > 10 || rating.getScore() < 1) {
            result.addMessage("Rating score is out of range. Needs to be 1-10.", ResultType.INVALID);
        }

        return result;
    }
}
