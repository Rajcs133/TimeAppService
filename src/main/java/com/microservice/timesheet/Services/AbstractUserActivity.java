package com.microservice.timesheet.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

/**
 * Abstract class representing user activity related to time sheets.
 * 
 * This abstract class provides common methods related to time sheet operations.
 * The actual implementation of performing time sheet tasks is deferred to the subclasses.
 */
public abstract class AbstractUserActivity {

    @Autowired
    private ITimeSheet loTimeSheetService;

    /**
     * Fills a time sheet entry.
     * 
     * @param poTimeSheetEntity The time sheet entry to be saved.
     * @return TimeSheetDto The saved time sheet entry.
     */
    public TimeSheetDto fillTimeSheet(TimeSheetDto poTimeSheetEntity) {
        return loTimeSheetService.saveTimeSheetEntity(poTimeSheetEntity);
    }

    /**
     * Retrieves a time sheet entry based on the Time sheet ID.
     * 
     * @param id The ID of the time sheet entry to retrieve.
     * @return TimeSheetDto The retrieved time sheet entry.
     * @throws Exception if an error occurs during the operation.
     */
    public TimeSheetDto getEntryBasedOnTimeSheetId(int id) throws Exception {
        return loTimeSheetService.getTimeSheetEntry(id);
    }

    /**
     * Retrieves time sheet entries based on pagination and sorting.
     * 
     * @param lsUserId    ID of the user whose time sheet entries to retrieve.
     * @param offset      Offset for pagination.
     * @param page size    Size of each page.
     * @param psFieldName Name of the field to sort by.
     * @return Page<TimeSheetEntity> Page of TimeSheetEntity objects.
     * @throws CustomBusinessException if an error occurs during the operation.
     */
    public Page<TimeSheetEntity> getEntriesBasedOnUserId(Integer lsUserId, Integer offset, Integer pagesize,
            String psFieldName) throws CustomBusinessException {
        return loTimeSheetService.getEntriesBasedOnUserId(lsUserId, offset, pagesize, psFieldName);
    }

    /**
     * Performs a time sheet task based on the provided mode.
     * 
     * This method is abstract and must be implemented by subclasses.
     * 
     * @param TimeSheet The array of time sheet entries to process.
     * @param lsMode    The mode of the task (e.g., SUBMIT, APPROVE, REJECT).
     * @return TimeSheetDto[] The updated array of time sheet entries.
     * @throws CustomBusinessException if an error occurs during time sheet processing.
     */
    public abstract TimeSheetDto[] performTimeSheetTask(TimeSheetDto[] TimeSheet, String lsMode)
            throws CustomBusinessException;
}
