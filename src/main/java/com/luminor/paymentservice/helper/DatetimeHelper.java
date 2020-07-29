package com.luminor.paymentservice.helper;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.HOURS;

@Component
public class DatetimeHelper {
    private static final int HOUR_IN_SECONDS = 3600;

    public boolean isSameDay(LocalDateTime dateTime) {
        return isSameDay(LocalDateTime.now(), dateTime);
    }

    public boolean isSameDay(LocalDateTime dateTime, LocalDateTime dateTime2) {
        return dateTime.toLocalDate().isEqual(dateTime2.toLocalDate());
    }

    public long completeHourDifference(LocalDateTime dateTime) {
        return completeHourDifference(dateTime, LocalDateTime.now());
    }

    public long completeHourDifference(LocalDateTime dateTime, LocalDateTime dateTime2) {
        return Math.abs(dateTime.until(dateTime2, HOURS));
    }
}
