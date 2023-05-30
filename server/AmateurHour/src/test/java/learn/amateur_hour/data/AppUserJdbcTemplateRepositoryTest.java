package learn.amateur_hour.data;

import learn.amateur_hour.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 31;

    @Autowired
    AppUserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findByUsername() {
        AppUser thenue = repository.findByUsername("Thenue");
        assertEquals(1, thenue.getAppUserId());
        assertEquals("Thenue", thenue.getUsername());
    }

    @Test
    void create() {
        // all fields
        AppUser user = makeAppUser();
        AppUser actual = repository.create(user);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getAppUserId());

    }

//TODO: Update this test
    @Test
    void update() {
        AppUser user = makeAppUser();
        user.setEmail("newUser@email.com");
        assertEquals("newUser@email.com", user.getEmail());
    }

    private AppUser makeAppUser() {
        List<String> newRole = new ArrayList<>();
        newRole.add("USER");
        AppUser user = new AppUser(0,"newUser", "EMPTY BIO","Mie2foowae", "newUser@email.com",false, newRole);
        return user;
    }
}