package learn.amateur_hour.controllers;

import learn.amateur_hour.domain.Result;
import learn.amateur_hour.domain.RsvpService;
import learn.amateur_hour.models.EventCapacity;
import learn.amateur_hour.models.Rsvp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/rsvp")
@RequiredArgsConstructor
public class RsvpController {

    private final RsvpService service;

    @GetMapping("/enrollment/{eventId}")
    public ResponseEntity<Object> currentEnrollmentsForEvent(@PathVariable int eventId) {
        EventCapacity ec = service.currentEnrollmentsForEvent(eventId);
        if (ec == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ec);
    }


    @GetMapping("/{eventId}/{userId}")
    public ResponseEntity<Object> findByUserEvent(@PathVariable int userId, @PathVariable int eventId) {
        Rsvp rsvp = service.findByUserEvent(userId, eventId);
        if (rsvp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rsvp);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Rsvp rsvp) {
        Result<Rsvp> result = service.add(rsvp);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{eventId}/{userId}")
    public ResponseEntity<Object> deleteRsvp(@PathVariable int userId, @PathVariable int eventId) {
        if (service.deleteRsvp(userId, eventId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
