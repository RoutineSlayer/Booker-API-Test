package api.booking.models.request;

public record BookingDates(
        String checkIn,
        String checkOut
) {}