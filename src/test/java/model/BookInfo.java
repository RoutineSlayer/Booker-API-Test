package model;

import lombok.Data;

@Data
public class BookInfo {
    private String firstName;
    private String lastName;
    private int totalPrice;
    private boolean depositPaid;
    private BookingDates bookingDates;
    private String additionalNeeds;
}
