package api.booking.client;

import api.booking.models.request.BookingRequest;
import io.restassured.response.Response;

import static api.booking.Credential.getPassword;
import static api.booking.Credential.getUsername;
import static io.restassured.RestAssured.*;

public class BookAPIClient {

    public Response getBookingIds() {
        return given().spec(requestSpecification).basePath("booking").get()
                .then().spec(responseSpecification).extract().response();
    }

    public Response getBooking(String bookingId) {
        return given().spec(requestSpecification).basePath("booking/" + bookingId).get()
                .then().spec(responseSpecification).extract().response();
    }

    public Response createBooking(BookingRequest book) {
        return given().spec(requestSpecification).basePath("booking").body(book).post()
                .then().spec(responseSpecification).extract().response();
    }

    public void deleteBooking(String bookingId) {
        given().spec(requestSpecification).auth().preemptive().basic(getUsername(), getPassword())
                .basePath("booking/" + bookingId)
                .delete()
                .then().spec(responseSpecification);
    }
}
