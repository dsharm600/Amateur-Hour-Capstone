package learn.amateur_hour.controllers;

import learn.amateur_hour.domain.Result;
import learn.amateur_hour.domain.TagService;
import learn.amateur_hour.models.Event;
import learn.amateur_hour.models.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService service;

    @GetMapping("/all")
    public List<Tag> findAllTags() {
        return service.findAllTags();
    }

    @GetMapping("/id/{tagId}")
    public ResponseEntity<Object> findById(@PathVariable int tagId) {
        Tag tag = service.findById(tagId);

        if (tag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/events/{tagId}")
    public List<Event> findEventsByTag(@PathVariable int tagId) {
        return service.findEventsByTag(tagId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Tag tag) {
        Result<Tag> result = service.add(tag);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }


    @PutMapping("/id/{tagId}")
    public ResponseEntity<Object> update(@PathVariable int tagId, @RequestBody Tag tag) {
        if (tagId != tag.getTagId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Tag> result = service.update(tag);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/id/{tagId}")
    public ResponseEntity<Object> delete(@PathVariable int tagId) {
        if (service.deleteById(tagId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
