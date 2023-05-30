package learn.amateur_hour.data;

import learn.amateur_hour.data.mappers.EventMapper;
import learn.amateur_hour.data.mappers.TagMapper;
import learn.amateur_hour.models.Event;
import learn.amateur_hour.models.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TagJdbcTemplateRepository implements TagRepository {
    private final JdbcTemplate jdbcTemplate;

    public TagJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // NOTE: CREATE new Tag
    @Override
    public Tag add(Tag tag) {
        final String sql = "insert into tag (tag_name) values (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getTagName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        tag.setTagId(keyHolder.getKey().intValue());
        return tag;
    }

    @Override
    public List<Event> findEventsByTag(int tagId) {
        final String sql = "SELECT e.event_id as event_id, username, event_title, event_capacity, event_address, city_name, state_code, event_date, event_description, event_notes, cancelled" +
                " FROM app_event e" +
                " inner join app_user u" +
                " on e.host_id = u.app_user_id" +
                " inner join state s" +
                " on s.state_id = e.state_id" +
                " inner join city c" +
                " on c.city_id = e.city_id" +
                " inner join event_tag t" +
                " on e.event_id = t.event_id" +
                " where tag_id = ?";
        return jdbcTemplate.query(sql, new EventMapper(), tagId);
    }

    // NOTE: READ Tag
    @Override
    public Tag findById(int tagId) {
        final String sql = "select tag_id, tag_name from tag " +
                "where tag_id = ?";

        return jdbcTemplate.query(sql, new TagMapper(), tagId).stream().findFirst().orElse(null);
    }

    @Override
    public Tag findByName(String tagName) {
        final String sql = "select tag_id, tag_name from tag " +
                "where tag_name = ?";

        return jdbcTemplate.query(sql, new TagMapper(), tagName).stream().findFirst().orElse(null);
    }

    // NOTE: UPDATE Tag
    @Override
    public boolean update(Tag tag) {

        final String sql = "update tag set "
                + "tag_name = ? "
                + "where tag_id = ?";

        return jdbcTemplate.update(sql, tag.getTagName(), tag.getTagId()) > 0;
    }

    // Note: DELETE Tag
    @Override
    public boolean deleteById(int tagId) {
        return jdbcTemplate.update("delete from tag where tag_id = ?", tagId) > 0;
    }

    @Override
    public List<Tag> findAllTags() {
        final String sql = "select tag_id, tag_name from tag";
        return jdbcTemplate.query(sql, new TagMapper());
    }
}
