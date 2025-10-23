package api.book_api.client.positive;

import api.book_api.client.models.request.BookingRequest;
import api.book_api.client.models.response.BookingIDResponse;
import api.book_api.client.models.response.BookingResponse;
import api.book_api.client.models.response.CreateBookingResponse;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static io.restassured.RestAssured.*;

public class BookAPIPositiveClient {
    public List<BookingIDResponse> getAllBookingIds() {
        return given().spec(requestSpecification).basePath("booking").get()
                .then().spec(responseSpecification).extract().jsonPath().getList("", BookingIDResponse.class);
    }

    public BookingResponse getBooking(String bookingId) {
        return given().spec(requestSpecification).basePath("booking/" + bookingId).get()
                .then().spec(responseSpecification).extract().body().as(BookingResponse.class);
    }

    public CreateBookingResponse createBooking(BookingRequest book) {
        return given().spec(requestSpecification).basePath("booking").body(book).post()
                .then().spec(responseSpecification).extract().body().as(CreateBookingResponse.class);
    }

    public void deleteBooking(String username, String password, String bookingId) {
        given().spec(requestSpecification).auth().preemptive().basic(username, password)
                .basePath("booking/" + bookingId)
                .delete()
                .then().spec(responseSpecification);
    }
}
