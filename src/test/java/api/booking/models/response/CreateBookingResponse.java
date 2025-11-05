package api.booking.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateBookingResponse(
        String bookingId,
        @JsonProperty("booking")
        BookingResponse bookingResponse
) {}
