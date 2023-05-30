package learn.amateur_hour.security;

import learn.amateur_hour.App;
import learn.amateur_hour.data.AppUserRepository;
import learn.amateur_hour.domain.Result;
import learn.amateur_hour.domain.ResultType;
import learn.amateur_hour.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {
   private final AppUserRepository repository;
   private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository,
                          PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public AppUser create(String username, String password, String email) {
        validate(username);
        validatePassword(password);

        password = encoder.encode(password);

        AppUser appUser = new AppUser(0, username, "", password, email, false, List.of("User"));

        return repository.create(appUser);
    }

    public Result<AppUser> update(AppUser user) {
        Result<AppUser> result = validateUpdate(user);
        if (!result.isSuccess()) {
            return result;
        }


        if (!repository.update(user)) {
            String msg = String.format("update of %s failed", user.getUsername());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }

    public UserDetails loadUserById(int userId) throws UsernameNotFoundException {
        AppUser appUser = repository.findById(userId);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(userId + " not found");
        }

        return appUser;
    }


    //NOTE: VALIDATION
    private void validate(String username) {
        if (username == null || username.isBlank()) {
            throw new ValidationException("username is required");
        }

        if (username.length() > 50) {
            throw new ValidationException("username must be less than 50 characters");
        }

        if (repository.findByUsername(username) != null){
            throw new ValidationException("Username must be unique");
        }
    }

    private Result<AppUser> validateUpdate(AppUser user) {
        Result<AppUser> result = new Result<>();

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.addMessage("Username is required", ResultType.INVALID);
        }

        if (user.getUsername().length() > 50) {
            result.addMessage("username must be less than 50 characters", ResultType.INVALID);
        }

        if (user.getAppUserId() <= 0) {
            result.addMessage("a user Id must be set for an `update` operation", ResultType.INVALID);
        }

        return result;
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("password must be at least 8 characters");
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        if (digits == 0 || letters == 0 || others == 0) {
            throw new ValidationException("password must contain a digit, a letter, and a non-digit/non-letter");
        }
    }
}
