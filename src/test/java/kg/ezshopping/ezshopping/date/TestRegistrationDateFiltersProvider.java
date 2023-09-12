package kg.ezshopping.ezshopping.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestRegistrationDateFiltersProvider {
    public static final DateTimeFormatter iso_formatter = DateTimeFormatter.ISO_DATE_TIME;
    public static final LocalDateTime TEST_REGISTRATION_DATE =
            LocalDateTime.parse("2020-04-20T12:00:10Z", iso_formatter);

    public static final LocalDateTime TEST_START_DATE_FILTER =
            LocalDateTime.parse("2018-04-20T12:00:00Z", iso_formatter);

    public static final LocalDateTime TEST_END_DATE_FILTER =
            LocalDateTime.parse("2022-04-20T12:00:00Z", iso_formatter);
}
