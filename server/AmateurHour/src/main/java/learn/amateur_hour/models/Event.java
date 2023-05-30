package learn.amateur_hour.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    @Getter
    @Setter
    private boolean isCancelled;

    @Getter
    @Setter
    private int eventId;

    @Getter
    @Setter
    private String host;

    @Getter
    @Setter
    private String eventDescription;

    @Getter
    @Setter
    private String eventNotes;

    @Getter
    @Setter
    private String eventAddress;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    private LocalDateTime eventDate;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private int capacity;

    @Getter
    @Setter
    private List<Rating> ratings = new ArrayList<>();

    @Getter
    @Setter
    private List<Tag> tags = new ArrayList<>();
}
