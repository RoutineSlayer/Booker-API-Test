package api.booking.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.mapper.factory.DefaultJackson2ObjectMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.*;

public final class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    public static void initRestAssuredConfig() {
        baseURI = "https://restful-booker.herokuapp.com/";
        config = config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(
                new DefaultJackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Type cls, String charset) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CASE);
                        return objectMapper;
                    }
                })).logConfig(config.getLogConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        requestSpecification = new RequestSpecBuilder().setConfig(config).setBaseUri(baseURI)
                .setContentType(ContentType.JSON).addFilter(new AllureRestAssured()).build();

        responseSpecification = new ResponseSpecBuilder().build();
    }
}
