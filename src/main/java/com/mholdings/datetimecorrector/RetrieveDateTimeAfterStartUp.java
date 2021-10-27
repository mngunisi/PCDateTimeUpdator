package com.mholdings.datetimecorrector;

import com.mholdings.datetimecorrector.service.DateTimeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RetrieveDateTimeAfterStartUp
{
    @Autowired
    private DateTimeServiceImpl dateTimeService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        System.out.println("App started!! ");
        dateTimeService.updatePcDateTime();
    }
}
