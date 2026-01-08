package dev.sivalabs.meetup4j.events.domain.vo;

public record EventLocation(
        String venue,
        String virtualLink) {

    public EventLocation {
        if (venue == null && virtualLink == null) {
            throw new IllegalArgumentException("Either venue or virtualLink must be provided");
        }
    }

    public static EventLocation of(String venue, String virtualLink) {
        return new EventLocation(venue, virtualLink);
    }
}
