package dev.sivalabs.meetup4j.events.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EventLocation(
        String venue,
        String virtualLink) {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public EventLocation(
            @JsonProperty("venue") String venue,
            @JsonProperty("virtualLink") String virtualLink
    ) {
        this.venue = venue;
        this.virtualLink = virtualLink;

        if (venue == null && virtualLink == null) {
            throw new IllegalArgumentException("Either venue or virtualLink must be provided");
        }
    }

    public static EventLocation of(String venue, String virtualLink) {
        return new EventLocation(venue, virtualLink);
    }
}
