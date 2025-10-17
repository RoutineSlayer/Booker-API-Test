package api.models;

public record BookInfo(
        String firstName,
        String lastName,
        int totalPrice,
        boolean depositPaid,
        BookingDates bookingDates,
        String additionalNeeds
) {}