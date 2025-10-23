package api.book_api.client.models.request;

public record BookingRequest(
        String firstName,
        String lastName,
        int totalPrice,
        boolean depositPaid,
        BookingDates bookingDates,
        String additionalNeeds
) {}
