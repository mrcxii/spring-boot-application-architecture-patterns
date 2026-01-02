package dev.sivalabs.meetup4j.events.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static dev.sivalabs.meetup4j.shared.AssertUtil.requireNotNull;

public record EventDetails(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 10000, message = "Description cannot exceed 10000 characters")
        String description,

        @Size(max = 500, message = "Image URL cannot exceed 500 characters")
        @Pattern(regexp = "^https?://.*", message = "Image URL must be a valid HTTP/HTTPS URL")
        String imageUrl) {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public EventDetails(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("imageUrl") String imageUrl
    ) {
        this.title = requireNotNull(title, "title cannot be null");
        this.description = requireNotNull(description, "description cannot be null");
        this.imageUrl = imageUrl;
    }

    public static EventDetails of(String title, String description, String imageUrl) {
        return new EventDetails(title, description, imageUrl);
    }
}
