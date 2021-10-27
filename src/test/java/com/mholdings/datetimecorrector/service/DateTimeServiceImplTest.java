package com.mholdings.datetimecorrector.service;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateTimeServiceImplTest {
    private DateTimeServiceImpl dateTimeService;

    @Before
    public void setUp()
    {
        dateTimeService = new DateTimeServiceImpl();
    }

    @Test
    public void testRetrieveDateTime()
    {
        LocalDateTime retrievedDate = dateTimeService.getCurrentDateTimeFromSource();

        assertTrue(retrievedDate.toString().contains("2021"));
    }
}