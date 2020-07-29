package com.luminor.paymentservice.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.parse;
import static org.junit.jupiter.api.Assertions.*;

class DatetimeHelperTest {

    private DatetimeHelper datetimeHelper;

    @BeforeEach
    void setUp() {
        datetimeHelper = new DatetimeHelper();
    }

    @Test
    void isSameDayShouldBeFalseForDiffDays() {
        assertFalse(datetimeHelper.isSameDay(parse("2020-07-08T20:00:00.000"), parse("2020-07-09T11:00:00.000")));
    }

    @Test
    void isSameDayShouldBeTrueForDiffDays() {
        assertTrue(datetimeHelper.isSameDay(parse("2020-07-08T20:00:00.000"), parse("2020-07-08T11:00:00.000")));
    }

    @Test
    void completeHourDifferenceTest() {
        assertEquals(9, datetimeHelper
                .completeHourDifference(parse("2020-07-08T20:00:00.000"), parse("2020-07-08T11:00:00.000")));
    }

    @Test
    void completeHourDifferenceTest2() {
        assertEquals(3, datetimeHelper
                .completeHourDifference(parse("2020-07-08T11:50:00.000"), parse("2020-07-08T15:49:00.000")));
    }

    @Test
    void completeHourDifferenceTest3() {
        assertEquals(0, datetimeHelper
                .completeHourDifference(parse("2020-07-08T11:20:00.000"), parse("2020-07-08T11:49:00.000")));
    }
}