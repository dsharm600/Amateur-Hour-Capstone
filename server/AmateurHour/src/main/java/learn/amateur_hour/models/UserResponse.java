package learn.amateur_hour.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    @Getter
    @Setter
    private  String appUserBio;

    @Getter
    @Setter
    private  String email;

    @Getter
    @Setter
    private List<String> roles = new ArrayList<>();

    @Getter
    @Setter
    private int appUserId;

    @Getter
    @Setter
    private  String username;
    
    public UserResponse(AppUser user) {
    this.appUserBio = user.getAppUserBio();
    this.email = user.getEmail();
    this.roles = user.getRoles();
    this.appUserId = user.getAppUserId();
    this.username = user.getUsername();

    }


    // constructer that only use these items 
}
