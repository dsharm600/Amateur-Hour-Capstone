package learn.amateur_hour.models;

import lombok.Getter;
import lombok.Setter;

public class Rating {
    @Getter
    @Setter
    private int ratingId;

    @Getter
    @Setter
    private int appUserId;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private int eventId;

    @Getter
    @Setter
    private String comment;

    @Getter
    @Setter
    private int score;
}
