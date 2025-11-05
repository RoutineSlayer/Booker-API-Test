package api.booking.tests.positivie;

import api.booking.client.BookAPIClient;
import api.booking.models.request.BookingDates;
import api.booking.models.request.BookingRequest;
import api.booking.models.response.BookingIDResponse;
import api.booking.models.response.BookingResponse;
import api.booking.models.response.CreateBookingResponse;
import api.booking.tests.Config;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookAPIPositiveTest {
    private BookAPIClient bookAPIClient;

    @BeforeEach
    void setUp() {
        bookAPIClient = new BookAPIClient();
        Config.initRestAssuredConfig();
    }

    @Owner("Luna")
    @Feature("Booking API")
    @DisplayName("Проверка получения списка всех Booking Id")
    @Story("Клиент должен иметь возможность получить список всех Booking Id")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void getAllBookingIds_CheckTheSizeIsOver10_ReturnSizeGreaterThanOrEqualTo10() {
        final List<BookingIDResponse> bookingIds = bookAPIClient.getBookingIds()
                .jsonPath().getList("", BookingIDResponse.class);

        assertThat(bookingIds.size(), allOf(notNullValue(), greaterThanOrEqualTo(10)));
    }

    @Owner("Luna")
    @Feature("Booking API")
    @DisplayName("Проверка получения Booking")
    @Test
    void getBookingById_CheckFirstNameIsNotEmpty_ReturnFirstNameNotEmpty() {
        final BookingResponse booking = bookAPIClient.getBooking("1").as(BookingResponse.class);

        assertThat(booking.firstName(), allOf(notNullValue(), not(emptyString())));
    }

    @Owner("Luna")
    @Feature("Booking API")
    @DisplayName("Проверка получения списка всех Booking Id")
    @ParameterizedTest
    @CsvSource({"4", "2", "3"})
    void getBookingById_CheckFirstNameIsNotEmptyMix_ReturnFirstNameNotEmpty(String bookingId) {
        final BookingResponse booking = bookAPIClient.getBooking(bookingId).as(BookingResponse.class);

        assertThat(booking.firstName(), allOf(notNullValue(), not(emptyString())));
    }

    @Owner("Luna")
    @Feature("Booking API")
    @DisplayName("Проверка создания Booking с валидными данными")
    @Test
    void createBooking_WithValidData_ReturnFirstNameEqualsToAnna() {
        final BookingDates bookingDates = new BookingDates(
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString()
        );
        final BookingRequest bookingRequest = new BookingRequest(
                "Анна",
                "Сидорова",
                200,
                false,
                bookingDates,
                "Needs"
        );

        final CreateBookingResponse createdBooking = bookAPIClient.createBooking(bookingRequest)
                .as(CreateBookingResponse.class);

        assertThat(createdBooking.bookingId(), notNullValue());
        assertThat(createdBooking.bookingResponse().firstName(), equalTo("Анна"));
    }

    @Owner("Luna")
    @Feature("Booking API")
    @DisplayName("Проверка удаления Booking с валидными данными")
    @Test
    void deleteBooking_WithValidBookingId_ReturnStatusCodeIs404() {
        final BookingDates bookingDates = new BookingDates(
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString()
        );
        final BookingRequest bookingRequest = new BookingRequest(
                "Анна",
                "Сидорова",
                200,
                false,
                bookingDates,
                "Needs"
        );

        final CreateBookingResponse createdBooking = bookAPIClient.createBooking(bookingRequest)
                .as(CreateBookingResponse.class);

        bookAPIClient.deleteBooking(createdBooking.bookingId());

        final Response response = bookAPIClient.getBooking(createdBooking.bookingId());

        assertThat(response.statusCode(), is(404));
    }
}
