package dev.sivalabs.meetup4j.events.domain.vo;

import dev.sivalabs.meetup4j.shared.AssertUtil;

public record EventDetails(
        String title,
        String description,
        String imageUrl) {

    public EventDetails {
        AssertUtil.requireNotNull(title, "title cannot be null");
        AssertUtil.requireSize(title, 3, 200, "title");
        AssertUtil.requireNotNull(description, "description cannot be null");
        AssertUtil.requireSize(description, 1, 10000, "description");
        AssertUtil.requireSize(imageUrl, 1, 500, "imageUrl");
        AssertUtil.requirePattern(imageUrl, "^https?://.*", "Image URL must be a valid HTTP/HTTPS URL");
    }

    public static EventDetails of(String title, String description, String imageUrl) {
        return new EventDetails(title, description, imageUrl);
    }
}
