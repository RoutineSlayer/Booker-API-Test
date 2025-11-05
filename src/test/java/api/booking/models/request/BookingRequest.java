package api.booking.models.request;

public record BookingRequest(
        String firstName,
        String lastName,
        int totalPrice,
        boolean depositPaid,
        BookingDates bookingDates,
        String additionalNeeds
) {}
