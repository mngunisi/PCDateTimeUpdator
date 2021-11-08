package com.mholdings.datetimecorrector;

import com.mholdings.datetimecorrector.service.DateTimeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class DateTimeCorrectorApplication {
	public static final Logger LOGGER = LoggerFactory.getLogger(DateTimeCorrectorApplication.class);
	@Autowired
	private DateTimeServiceImpl dateTimeService;

	public static void main(String[] args) {
		SpringApplication.run(DateTimeCorrectorApplication.class, args);
	}

	@PostConstruct
	public void updateDateAndTimeOnStartup() {
		LOGGER.info("running date time updater on App Start Up ...");
		dateTimeService.updatePcDateTime();
	}

	@Scheduled(cron = "${datetime.corrector.update.cron.schedule}")
	public void updateDateAndTimeCronJob() {
		LOGGER.info("running date time updater cron job ...");
		dateTimeService.updatePcDateTime();
	}
}
