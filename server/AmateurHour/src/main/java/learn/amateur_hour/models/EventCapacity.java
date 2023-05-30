package learn.amateur_hour.models;

import lombok.Getter;
import lombok.Setter;

public class EventCapacity {
    @Getter
    @Setter
    int maxCapacity;

    @Getter
    @Setter
    int currentCapacity;
}
