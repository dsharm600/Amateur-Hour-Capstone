package learn.amateur_hour.data;

import learn.amateur_hour.models.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserRepository {
    @Transactional
    AppUser findByUsername(String username);

    @Transactional
    AppUser create(AppUser user);

    @Transactional
    boolean update(AppUser user);

    @Transactional
    AppUser findById(int userId);
}
