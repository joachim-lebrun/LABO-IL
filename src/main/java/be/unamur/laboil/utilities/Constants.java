package be.unamur.laboil.utilities;

import java.time.format.DateTimeFormatter;

/**
 * @author Joachim Lebrun on 15-08-18
 */
public class Constants {

    private Constants() {
    }

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter SQL_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
}
