package com.microservice.timesheet.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

@Service
public class TimeSheetService {

    @Autowired
    private UserFactory loUserFactory;

    /**
     * Retrieves a user object based on the provided role.
     * 
     * This method delegates the creation of the user object to the UserFactory,
     * which is responsible for creating user objects based on different roles.
     * 
     * @param lsRole The role of the user for which the object needs to be retrieved.
     * @return AbstractUserActivity The user object corresponding to the provided role.
     */
    public AbstractUserActivity getUserObject(String lsRole) {
        return ((UserFactory) loUserFactory).getUserObj(lsRole);
    }

    /**
     * Saves a time sheet entry.
     * 
     * @param poTimeSheetEntity The time sheet entry to be saved.
     * @return TimeSheetDto The saved time sheet entry.
     * @throws CustomBusinessException if an error occurs during the operation.
     */
    public TimeSheetDto saveTimeSheetEntry(TimeSheetDto poTimeSheetEntity) throws CustomBusinessException {
        TimeSheetDto lsTimeSheetEntity = (TimeSheetDto) getUserObject(Role.EMPLOYEE.toString())
                .fillTimeSheet(poTimeSheetEntity);
        return lsTimeSheetEntity;
    }

    /**
     * Retrieves a time sheet entry based on the Time sheet ID.
     * 
     * @param id The ID of the time sheet entry to retrieve.
     * @return TimeSheetDto The retrieved time sheet entry.
     * @throws Exception if an error occurs during the operation.
     */
    public TimeSheetDto getEntryBasedOnTimeSheetId(int id) throws Exception {
        TimeSheetDto lsTimeSheetDto = new TimeSheetDto();
        lsTimeSheetDto = (TimeSheetDto) getUserObject(Role.EMPLOYEE.toString()).getEntryBasedOnTimeSheetId(id);
        return lsTimeSheetDto;
    }

    /**
     * Retrieves time sheet entries based on pagination and sorting.
     * 
     * @param psUserId    ID of the user whose time sheet entries to retrieve.
     * @param offset      Offset for pagination.
     * @param page size    Size of each page.
     * @param psFieldName Name of the field to sort by.
     * @return Page<TimeSheetEntity> Page of TimeSheetEntity objects.
     * @throws Exception if an error occurs during the operation.
     */
    public Page<TimeSheetEntity> getEntriesBasedOnUserId(Integer psUserId, Integer offset, Integer pagesize,
            String psFieldName) throws Exception {
        return (Page<TimeSheetEntity>) getUserObject(Role.EMPLOYEE.toString()).getEntriesBasedOnUserId(psUserId,
                offset, pagesize, psFieldName);
    }

    /**
     * Performs a time sheet task based on the provided role and mode.
     * 
     * @param loTimeSheet The array of time sheet entries to process.
     * @param lsRole      The role of the user performing the task.
     * @param lsMode      The mode of the task (e.g., SUBMIT, APPROVE, REJECT).
     * @return TimeSheetDto[] The updated array of time sheet entries.
     * @throws CustomBusinessException if an error occurs during time sheet processing.
     */
    public TimeSheetDto[] performTimeSheetTask(TimeSheetDto[] loTimeSheet, String lsRole, String lsMode)
            throws CustomBusinessException {
        AbstractUserActivity emp = getUserObject(lsRole);
        return (TimeSheetDto[]) (emp).performTimeSheetTask(loTimeSheet, lsMode);
    }
}
