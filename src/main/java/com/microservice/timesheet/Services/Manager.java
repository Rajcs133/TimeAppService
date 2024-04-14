package com.microservice.timesheet.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

/**
 * Service class representing a Manager user for time sheet operations.
 * 
 * This class extends the AbstractUserActivity class and provides specific
 * implementations for manager-related time sheet tasks.
 */
@Service(Manager.BEAN_ID)
public class Manager extends AbstractUserActivity {

    public static final String BEAN_ID = "MANAGER";

    @Autowired
    ITimeSheet loTimeSheetService;

    /**
     * Approves time sheet entries.
     * 
     * @param loTimeSheet The array of time sheet entries to approve.
     * @return TimeSheetDto[] The updated array of time sheet entries after approval.
     */
    private TimeSheetDto[] approveTimeSheet(TimeSheetDto[] loTimeSheet) {
        return loTimeSheetService.approveTimeSheetEntity(loTimeSheet);
    }

    /**
     * Performs a time sheet task based on the provided mode for a Manager.
     * 
     * This method handles the approval or rejection of time sheet entries for a Manager.
     * 
     * @param lsTimeSheet The array of time sheet entries to process.
     * @param lsMode      The mode of the task (e.g., APPROVE, REJECT).
     * @return TimeSheetDto[] The updated array of time sheet entries.
     * @throws CustomBusinessException if an error occurs during time sheet processing.
     */
    @Override
    public TimeSheetDto[] performTimeSheetTask(TimeSheetDto[] lsTimeSheet, String lsMode)
            throws CustomBusinessException {
        if (lsMode.equalsIgnoreCase(Mode.APPROVE.toString())) {
            lsTimeSheet = approveTimeSheet(lsTimeSheet);
            return lsTimeSheet;
        }

        if (lsMode.equalsIgnoreCase(Mode.REJECT.toString())) {
            TimeSheetDto[] poTimeSheetPaged = rejectTimeSheet(lsTimeSheet);
            return poTimeSheetPaged;
        }
        
        return lsTimeSheet;
    }

    /**
     * Rejects time sheet entries.
     * 
     * @param lsTimeSheet The array of time sheet entries to reject.
     * @return TimeSheetDto[] The updated array of time sheet entries after rejection.
     */
    private TimeSheetDto[] rejectTimeSheet(TimeSheetDto[] lsTimeSheet) {
        return loTimeSheetService.rejectTimeSheetEntity(lsTimeSheet);
    }
}
