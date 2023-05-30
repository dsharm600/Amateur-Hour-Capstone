package learn.amateur_hour.controllers;

import learn.amateur_hour.domain.RatingService;
import learn.amateur_hour.domain.Result;
import learn.amateur_hour.models.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService service;

    @GetMapping("user/{userId}")
    public List<Rating> findAllByUserId(@PathVariable int userId) {
        return service.findAllByUserId(userId);
    }

    @GetMapping("event/{eventId}")
    public List<Rating> findAllByEventTag(@PathVariable int eventId) {
        return service.findAllByEventId(eventId);
    }

    @GetMapping("{ratingId}")
    public ResponseEntity<Object> findById(@PathVariable int ratingId) {
        Rating rating = service.findById(ratingId);
        if (rating == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rating);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Rating rating) {
        Result<Rating> result = service.add(rating);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Object> update(@RequestBody Rating rating, @PathVariable int ratingId) {
        if (rating.getRatingId() != ratingId) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Rating> result = service.update(rating);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@PathVariable int ratingId) {
        if (service.deleteById(ratingId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
