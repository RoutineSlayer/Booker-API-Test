package api.book_api.client.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateBookingResponse(
        String bookingId,
        @JsonProperty("booking")
        BookingResponse bookingResponse
) {}
