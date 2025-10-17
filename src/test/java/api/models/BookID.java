package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookID(
        @JsonProperty("bookingid")
        long id
) {}
