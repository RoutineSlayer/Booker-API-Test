package api.tests;

import api.Config;
import api.methods.BookAPIClient;
import api.models.BookID;
import api.models.BookInfo;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookAPITest {
    private final static String TOKEN = "Basic YWRtaW46cGFzc3dvcmQxMjM=";
    private BookAPIClient bookAPIClient;
    private Config config;

    @BeforeEach
    void setUp() {
        bookAPIClient = new BookAPIClient();
        config = new Config();
    }

    @Test
    void getAllBookIDsAndCheckTheSizeIsOver100ReturnTrue() {
        Response response = bookAPIClient.getAllBooks();

        List<BookID> bookIDS = response.jsonPath().getList(".", BookID.class);
        System.out.println(bookIDS.size());
        assertTrue(bookIDS.size() > 10);
    }

    @Test
    void getBookByIdAndCheckFirstNameIsNotEmptyReturnFalse() {
        BookInfo bookInfo = bookAPIClient.getBook("1");
        assertFalse(bookInfo.firstName().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void getBookByIdAndCheckFirstNameIsNotEmptyMixReturnFalse(String id) {
        BookInfo bookInfo = bookAPIClient.getBook(id);
        assertFalse(bookInfo.firstName().isEmpty());
    }
}

