package learn.amateur_hour.controllers;

import learn.amateur_hour.domain.EventService;
import learn.amateur_hour.domain.Result;
import learn.amateur_hour.models.AppUser;
import learn.amateur_hour.models.Event;
import learn.amateur_hour.security.JwtConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;
    private final JwtConverter jwtConverter;

    @GetMapping("/{stateCode}/{page}")
    public List<Event> findFutureEventsByState(@PathVariable String stateCode,@PathVariable int page) {
        return service.findFutureEventsByState(stateCode, page);
    }

    @GetMapping("/history/{page}")
    public List<Event> findPastEvents(@PathVariable int page) {
        return service.findPastEvents(page);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> findById(@PathVariable int eventId) {
        Event event = service.findById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Event event) {
        Result<Event> result = service.add(event);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Object> update(@PathVariable int eventId, @RequestBody Event event, @RequestHeader("Authorization")String jwtToken) {
        if (eventId != event.getEventId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        AppUser user = jwtConverter.getUserFromToken(jwtToken);
        Result<Event> result = service.update(event, user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Object> cancel(@PathVariable int eventId, @RequestHeader("Authorization")String jwtToken) {
        AppUser user = jwtConverter.getUserFromToken(jwtToken);

        Result<Event> result = service.cancelById(eventId, user);
                if (result.isSuccess()){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

        return ErrorResponse.build(result);
    }
}
