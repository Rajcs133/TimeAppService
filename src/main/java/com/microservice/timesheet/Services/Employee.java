package com.microservice.timesheet.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

/**
 * Service class representing an Employee user for time sheet operations.
 * 
 * This class extends the AbstractUserActivity class and provides specific
 * implementations for employee-related time sheet tasks.
 */
@Service(Employee.BEAN_ID)
public class Employee extends AbstractUserActivity {

    public static final String BEAN_ID = "EMPLOYEE";

    @Autowired
    ITimeSheet loTimeSheetService;

    /**
     * Performs a time sheet task based on the provided mode for an Employee.
     * 
     * This method handles the submission of time sheet entries for an Employee.
     * 
     * @param TimeSheet   The array of time sheet entries to process.
     * @param ModeValue   The mode of the task (e.g., SUBMIT).
     * @return TimeSheetDto[] The updated array of time sheet entries.
     * @throws CustomBusinessException if an error occurs during time sheet processing.
     */
    @Override
    public TimeSheetDto[] performTimeSheetTask(TimeSheetDto[] TimeSheet, String ModeValue)
            throws CustomBusinessException {
        TimeSheetDto[] loTimeSheetDto = null;

        if (ModeValue.equalsIgnoreCase(Mode.SUBMIT.toString())) {
            loTimeSheetDto = submitTimeSheetEntry(TimeSheet);
        }

        return loTimeSheetDto;
    }

    /**
     * Submits time sheet entries.
     * 
     * @param loTimeSheet The array of time sheet entries to submit.
     * @return TimeSheetDto[] The updated array of time sheet entries after submission.
     */
    private TimeSheetDto[] submitTimeSheetEntry(TimeSheetDto[] loTimeSheet) {
        return loTimeSheetService.submitTimeSheetEntity(loTimeSheet);
    }
}
