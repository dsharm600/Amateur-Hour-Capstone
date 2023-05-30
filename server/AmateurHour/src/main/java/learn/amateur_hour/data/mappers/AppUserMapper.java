package learn.amateur_hour.data.mappers;

import learn.amateur_hour.models.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppUserMapper implements RowMapper<AppUser> {
    private final List<String> roles;

    public AppUserMapper(List<String> roles) {
        this.roles = roles;
    }
    @Override
    public AppUser mapRow(ResultSet rs, int i) throws SQLException {
        return new AppUser(
                rs.getInt("app_user_id"),
                rs.getString("username"),
                rs.getString("app_user_bio"),
                rs.getString("password_hash"),
                rs.getString("email"),
                rs.getBoolean("disabled"),
                roles);
    }
}
