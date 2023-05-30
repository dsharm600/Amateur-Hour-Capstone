package learn.amateur_hour.domain;

import learn.amateur_hour.data.TagRepository;
import learn.amateur_hour.models.Event;
import learn.amateur_hour.models.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;

    public List<Tag> findAllTags() {
        return repository.findAllTags();
    }

    public List<Event> findEventsByTag(int tagId) {
        return repository.findEventsByTag(tagId);
    }

    public Tag findById(int tagId) {
        return repository.findById(tagId);
    }

    public Result<Tag> add(Tag tag) {
        Result<Tag> result = validate(tag);

        if (tag.getTagId() != 0) {
            result.addMessage("Tag id can't be set when adding tag", ResultType.INVALID);
            return result;
        }

        tag = repository.add(tag);

        if (tag == null) {
            result.addMessage("Tag must not be a duplicate", ResultType.INVALID);
            return result;
        }

        result.setPayload(tag);
        return result;
    }

    public Result<Tag> update(Tag tag) {
        Result<Tag> result = validate(tag);

        if (!result.isSuccess())
            return result;

        if (tag.getTagId() <= 0) {
            result.addMessage("A valid tag id must be provided to update", ResultType.INVALID);
            return result;
        }

        if (!repository.update(tag)) {
            String msg = String.format("Tag Id: %s, not found", tag.getTagId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int tagId) {
        return repository.deleteById(tagId);
    }

    private Result<Tag> validate(Tag tag) {
        Result<Tag> result = new Result<>();

        if (tag == null) {
            result.addMessage("Tag cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(tag.getTagName())) {
            result.addMessage("Tag can't be blank", ResultType.INVALID);
        }

        if (repository.findByName(tag.getTagName()) != null) {
            result.addMessage("Tag name already exists", ResultType.INVALID);
        }

        return result;
    }
}
