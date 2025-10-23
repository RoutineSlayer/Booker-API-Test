package api.book_api.client.models.request;

public record BookingDates(
        String checkIn,
        String checkOut
) {}