package com.mholdings.datetimecorrector.service;

import java.time.LocalDateTime;

public interface DateTimeService {
    LocalDateTime getCurrentDateTimeFromSource();
    void updatePcDateTime();
}
