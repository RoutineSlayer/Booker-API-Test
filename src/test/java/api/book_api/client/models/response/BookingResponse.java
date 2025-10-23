package api.book_api.client.models.response;

public record BookingResponse(
        String firstName,
        String lastName,
        int totalPrice,
        boolean depositPaid,
        BookingDates bookingDates,
        String additionalNeeds
) {}