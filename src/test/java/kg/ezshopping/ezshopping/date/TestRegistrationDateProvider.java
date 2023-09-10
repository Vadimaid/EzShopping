package kg.ezshopping.ezshopping.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestRegistrationDateProvider {
    public static final DateTimeFormatter iso_formatter = DateTimeFormatter.ISO_DATE_TIME;
    public static final LocalDateTime TEST_REGISTRATION_DATE =
            LocalDateTime.parse("2020-04-20T12:00:00", iso_formatter);
}
