package api.booking.models.response;

public record BookingResponse(
        String firstName,
        String lastName,
        int totalPrice,
        boolean depositPaid,
        BookingDates bookingDates,
        String additionalNeeds
) {}