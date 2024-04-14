package com.microservice.timesheet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservice.timesheet.dto.TimeSheetMapper;

@Configuration
public class TimeSheetConfig {


    @Bean
    TimeSheetMapper timeSheetMapper() {
        return TimeSheetMapper.INSTANCE;
    }

}
