package com.mholdings.datetimecorrector.service;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.*;

@Service
public class DateTimeServiceImpl implements DateTimeService {
    @Value("${datetime.corrector.date.source.server}")
    private String date_time_source_server;
    public static final Logger LOGGER = LoggerFactory.getLogger(DateTimeServiceImpl.class);

    @Override
    public void updatePcDateTime() {
        LocalDateTime dateTime = getCurrentDateTimeFromSource();
        LocalDateTime pcDateTime = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime zeroDatedMinutes = dateTime.withSecond(0).withNano(0);

        if (pcDateTime.compareTo(zeroDatedMinutes) < 1)
        {
            LOGGER.info("Updating pc date and time");
            try {
                Runtime.getRuntime().exec("cmd /c date " + getDateFromDateTime(dateTime));
                Runtime.getRuntime().exec("cmd /c time " + getTimeFromDateTime(dateTime));
            } catch (Exception ex) {
                LOGGER.info("Unable to update pc date and time", ex);
            }
        }
    }

    @Override
    public LocalDateTime getCurrentDateTimeFromSource() {
        LocalDateTime dateTime = LocalDateTime.now();
        try
        {
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName(date_time_source_server);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            NtpV3Packet message = timeInfo.getMessage();
            long serverTime = message.getTransmitTimeStamp().getTime();
            Instant instant = Instant.ofEpochMilli(serverTime);
            dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
        catch (Exception ex)
        {
            LOGGER.info("Failed to retrieve date time from internet server", ex);
        }

        return dateTime;
    }

    private LocalDate getDateFromDateTime(LocalDateTime dateTime) {
        return LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
    }

    private LocalTime getTimeFromDateTime(LocalDateTime dateTime) {
        return LocalTime.of(dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
    }
}
