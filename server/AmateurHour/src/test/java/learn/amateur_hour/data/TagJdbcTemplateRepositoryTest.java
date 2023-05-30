package learn.amateur_hour.data;

import learn.amateur_hour.models.Event;
import learn.amateur_hour.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagJdbcTemplateRepositoryTest {

    @Autowired
    TagJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void addWorksWhenValid() {
        Tag tag = makeTag();
        tag = repository.add(tag);
        assertNotNull(tag);
        assertTrue(tag.getTagId() > 0);
    }

    @Test
    void findByIdWorksWhenExists() {
        Tag tag = repository.findById(2);
        assertNotNull(tag);

        assertEquals("Metal Working", tag.getTagName());
    }

    @Test
    void findEventsByTag() {
        List<Event> events = repository.findEventsByTag(1);
        assertTrue(events.size() > 0);
    }

    @Test
    void findByIdFailsWhenNotFound() {
        Tag tag = repository.findById(100);
        assertNull(tag);
    }

    @Test
    void findByNameFindsWhenExists() {
        Tag tag = repository.findByName("Metal Working");
        assertNotNull(tag);

        assertEquals(2, tag.getTagId());
    }

    @Test
    void findByNameFailsWhenNotFound() {
        Tag tag = repository.findByName("NotARealMeme");
        assertNull(tag);
    }

    @Test
    void updateWorksWhenValid() {
        Tag tag = makeTag();
        tag.setTagName("More Memes");
        tag.setTagId(1);

        assertTrue(repository.update(tag));
        assertEquals(tag.getTagName(), repository.findById(1).getTagName());
    }

    @Test
    void updateFailsWhenInvalid() {
        Tag tag = makeTag();
        tag.setTagId(100);

        assertFalse(repository.update(tag));
    }

    @Test
    void deleteByIdDeletesWhenExists() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));

        assertNull(repository.findById(3));
    }

    @Test
    void deleteByIdFailsWhenNotExisting() {
        assertFalse(repository.deleteById(100));
    }

    @Test
    void findAllTagsFindsAllTags() {
        assertTrue(repository.findAllTags().size() > 10);
    }

    private Tag makeTag() {
        Tag tag = new Tag();
        tag.setTagName("Memes");

        return tag;
    }
}