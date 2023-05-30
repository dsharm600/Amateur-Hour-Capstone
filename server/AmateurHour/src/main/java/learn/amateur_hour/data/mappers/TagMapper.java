package learn.amateur_hour.data.mappers;

import learn.amateur_hour.models.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag tag = new Tag();
        tag.setTagId(resultSet.getInt("tag_id"));
        tag.setTagName(resultSet.getString("tag_name"));

        return tag;
    }
}
