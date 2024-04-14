package com.microservice.timesheet.Services;

import org.springframework.data.domain.Page;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

public interface ITimeSheet {
	
	public TimeSheetDto saveTimeSheetEntity(TimeSheetDto poTimeSheetEntity);
		
	public TimeSheetDto[] approveTimeSheetEntity(TimeSheetDto[] poTimeSheetEntity);
	
	public TimeSheetDto getTimeSheetEntry(int lnUserId) throws CustomBusinessException;
	
	public Page<TimeSheetEntity> getEntriesBasedOnUserId(Integer psUserId,Integer psOffset,Integer psPageSize,String psFieldName) throws CustomBusinessException;

	public TimeSheetDto[] submitTimeSheetEntity(TimeSheetDto []poTimeSheetEntity);

	public TimeSheetDto[] rejectTimeSheetEntity(TimeSheetDto[] poTimeSheetEntity);
	
}
