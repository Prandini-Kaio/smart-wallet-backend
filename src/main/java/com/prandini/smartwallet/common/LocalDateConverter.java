package com.prandini.smartwallet.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * @author prandini
 * created 5/8/24
 */
public class LocalDateConverter {

    public static String BR_DATE_PATTERN = "dd/MM/yyyy";
    public static String BR_DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    public static String toBrazilianDateString(LocalDate date){
        return LocalDateConverter.format(date, BR_DATE_PATTERN);
    }

    public static String toBrazilianDateTimeString(LocalDateTime date){
        return LocalDateConverter.format(date, BR_DATETIME_PATTERN);
    }

    private static String format(LocalDateTime date, String pattern){
        if(date == null)
            return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    private static String format(LocalDate date, String pattern){
        if(date == null)
            return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }
}
