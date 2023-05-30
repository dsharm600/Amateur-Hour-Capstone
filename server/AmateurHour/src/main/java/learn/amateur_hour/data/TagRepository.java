package learn.amateur_hour.data;

import learn.amateur_hour.models.Event;
import learn.amateur_hour.models.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> findAllTags();

    List<Event> findEventsByTag(int tagId);

    // NOTE: READ Tag
    Tag findById(int tagId);

    Tag findByName(String tagName);

    // NOTE: CREATE new Tag
    Tag add(Tag tag);

    // NOTE: UPDATE Tag
    boolean update(Tag tag);

    // Note: DELETE Tag
    boolean deleteById(int tagId);
}
