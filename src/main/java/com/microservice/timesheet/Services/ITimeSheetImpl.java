package com.microservice.timesheet.Services;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.Repositories.TimeSheetRepo;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.dto.TimeSheetMapper;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

/**
 * Service implementation class for time sheet operations.
 * 
 * This class provides implementations for various time sheet operations like
 * saving, approving, rejecting, and submitting time sheet entries.
 */
@Service
public class ITimeSheetImpl implements ITimeSheet {

	@Autowired
	TimeSheetRepo loRepo;

	@Autowired
	private TimeSheetMapper timeSheetMapper;

	/**
	 * Saves a time sheet entity.
	 * 
	 * @param poTimeSheetDto The time sheet DTO to be saved.
	 * @return TimeSheetDto The saved time sheet DTO.
	 */
	@Override
	public TimeSheetDto saveTimeSheetEntity(TimeSheetDto poTimeSheetDto) {
		TimeSheetEntity poTimeSheetEntity = timeSheetMapper.toEntity(poTimeSheetDto);
		TimeSheetEntity lsTimeSheetEntity = loRepo.save(poTimeSheetEntity);
		return timeSheetMapper.toDto(lsTimeSheetEntity);
	}

	/**
	 * Approves time sheet entries.
	 * 
	 * @param poTimeSheet The array of time sheet DTOs to be approved.
	 * @return TimeSheetDto[] The updated array of time sheet DTOs after approval.
	 */
	@Override
	public TimeSheetDto[] approveTimeSheetEntity(TimeSheetDto[] poTimeSheet) {
		return Arrays.stream(poTimeSheet).peek(timeSheet -> timeSheet.setLsStatus("Approved"))
				.toArray(TimeSheetDto[]::new);
	}

	/**
	 * Rejects time sheet entries.
	 * 
	 * @param poTimeSheet The array of time sheet DTOs to be rejected.
	 * @return TimeSheetDto[] The updated array of time sheet DTOs after rejection.
	 */
	@Override
	public TimeSheetDto[] rejectTimeSheetEntity(TimeSheetDto[] poTimeSheet) {
		return Arrays.stream(poTimeSheet).peek(timeSheet -> timeSheet.setLsStatus("Rejected"))
				.toArray(TimeSheetDto[]::new);
	}

	/**
	 * Submits time sheet entries.
	 * 
	 * @param poTimeSheet The array of time sheet DTOs to be submitted.
	 * @return TimeSheetDto[] The updated array of time sheet DTOs after submission.
	 */
	@Override
	public TimeSheetDto[] submitTimeSheetEntity(TimeSheetDto[] poTimeSheet) {
		return Arrays.stream(poTimeSheet).peek(timeSheet -> timeSheet.setLsStatus("Submitted"))
				.toArray(TimeSheetDto[]::new);
	}

	/**
	 * Retrieves a time sheet entry based on the provided ID.
	 * 
	 * @param lnId The ID of the time sheet entry to retrieve.
	 * @return TimeSheetDto The retrieved time sheet DTO.
	 * @throws CustomBusinessException if there is no entry for the given ID.
	 */
	@Override
	public TimeSheetDto getTimeSheetEntry(int lnId) throws CustomBusinessException {
		Optional<TimeSheetEntity> loTimeSheetEntity = loRepo.findById(lnId);
		(loTimeSheetEntity).orElseThrow(() -> new CustomBusinessException("There is no Entry for the given Id"));
		return timeSheetMapper.toDto(loTimeSheetEntity.get());
	}

	/**
	 * Retrieves time sheet entries based on pagination and sorting.
	 * 
	 * @param psId        ID of the user whose time sheet entries to retrieve.
	 * @param offset      Offset for pagination.
	 * @param pagesize    Size of each page.
	 * @param psFieldName Name of the field to sort by.
	 * @return Page<TimeSheetEntity> Page of time sheet entities.
	 * @throws CustomBusinessException if an error occurs during the operation.
	 */
	@Override
	public Page<TimeSheetEntity> getEntriesBasedOnUserId(Integer psId, Integer offset, Integer pagesize,
			String psFieldName) throws CustomBusinessException {
		if (pagesize != null && pagesize > 200) {
			throw new CustomBusinessException("PageSize cannot be greater than 200");
		}

		if (offset == null && pagesize == null) {
			offset = 0;
			pagesize = 20;
		}

		Sort sort;
		if (StringUtils.hasText(psFieldName)) {
			sort = Sort.by(Sort.Direction.ASC, psFieldName);
		} else {
			sort = Sort.unsorted();
		}

		PageRequest pageable = PageRequest.of(offset, pagesize, sort);

		Page<TimeSheetEntity> entityPage = loRepo.findByLsUserEntity_LsUserid(psId, pageable);

		return entityPage;
	}

}
