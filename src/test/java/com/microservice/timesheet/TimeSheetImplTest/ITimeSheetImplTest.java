package com.microservice.timesheet.TimeSheetImplTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.Entity.UserTimeSheetEntity;
import com.microservice.timesheet.Repositories.TimeSheetRepo;
import com.microservice.timesheet.Services.ITimeSheetImpl;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.dto.TimeSheetMapper;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ITimeSheetImplTest {

	
    @Mock
    private TimeSheetRepo timeSheetRepo;

    @Mock
    private TimeSheetMapper timeSheetMapper;

    @InjectMocks
    private ITimeSheetImpl service;
    
    
   

    // Test for saveTimeSheetEntity
    @Test
    public void saveTimeSheetEntity() {
        TimeSheetDto dto = new TimeSheetDto();
        TimeSheetEntity entity = createTimeSheetEntityStub("Submitted");
        TimeSheetDto savedDto = createTimeSheetdtoStub("Submitted");

        when(timeSheetMapper.toEntity(dto)).thenReturn(entity);
        when(timeSheetRepo.save(entity)).thenReturn(entity);
        when(timeSheetMapper.toDto(entity)).thenReturn(savedDto);

        TimeSheetDto result = service.saveTimeSheetEntity(dto);

        assertEquals(savedDto, result);
        verify(timeSheetMapper).toEntity(dto);
        verify(timeSheetRepo).save(entity);
        verify(timeSheetMapper).toDto(entity);
    }

  

	// Test for approveTimeSheetEntity
    @Test
    public void approveTimeSheetEntity() {
        TimeSheetDto dto1 = createTimeSheetdtoStub("Approved");
        TimeSheetDto dto2 = createTimeSheetdtoStub("Approved");
      
        TimeSheetDto[] dtos = new TimeSheetDto[]{dto1, dto2};
        TimeSheetDto[] approvedDtos = Arrays.stream(dtos)
                .peek(timeSheet -> timeSheet.setLsStatus("Approved"))
                .collect(Collectors.toList()).toArray(TimeSheetDto[] :: new);


        TimeSheetDto[] result = service.approveTimeSheetEntity(dtos);

        assertArrayEquals(approvedDtos, result);
        
    }

    // Test for rejectTimeSheetEntity (similar to approveTimeSheetEntity)
    @Test
    public void rejectTimeSheetEntity() {
        TimeSheetDto dto1 = createTimeSheetdtoStub("Rejected");
        TimeSheetDto dto2 = createTimeSheetdtoStub("Rejected");
        
        TimeSheetDto dtopass1 = createTimeSheetdtoStub("Submitted");
        TimeSheetDto dtopass2 = createTimeSheetdtoStub("Submitted");

        
        TimeSheetDto[] rejectedDtos = new TimeSheetDto[]{dto1, dto2};
        TimeSheetDto[] passedforRejectionDtos = new TimeSheetDto[]{dtopass1, dtopass2};
        
        TimeSheetDto[] result = service.rejectTimeSheetEntity(passedforRejectionDtos);

        assertTrue(Arrays.deepEquals(rejectedDtos,result));
       
    }
    
    
    @Test
    public void submitTimeSheetEntity() {
    	

        TimeSheetDto dto1 = createTimeSheetdtoStub("Submitted");
        TimeSheetDto dto2 = createTimeSheetdtoStub("Submitted");
        
        TimeSheetDto dtopass1 = createTimeSheetdtoStub("Saved");
        TimeSheetDto dtopass2 = createTimeSheetdtoStub("Saved");

        
        TimeSheetDto[] submittedDtos = new TimeSheetDto[]{dto1, dto2};
        TimeSheetDto[] passedforSubmission = new TimeSheetDto[]{dtopass1, dtopass2};
        
        TimeSheetDto[] result = service.submitTimeSheetEntity(passedforSubmission);

        assertTrue(Arrays.deepEquals(submittedDtos,result));
        	
    }

    // Test for getTimeSheetEntry
    @Test
    public void getTimeSheetEntry_found() {
        int id = 1;
        TimeSheetEntity entity = createTimeSheetEntityStub("Submitted");;
        TimeSheetDto dto = createTimeSheetdtoStub("Submitted");;

        when(timeSheetRepo.findById(id)).thenReturn(Optional.of(entity));
        when(timeSheetMapper.toDto(entity)).thenReturn(dto);

        TimeSheetDto result = null;
		try {
			result = service.getTimeSheetEntry(id);
		} catch (CustomBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        assertEquals(dto, result);
        verify(timeSheetRepo).findById(id);
        verify(timeSheetMapper).toDto(entity);
    }

    @Test
    public void getTimeSheetEntry_notFound() {
        int id = 1;

        when(timeSheetRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomBusinessException.class, () -> service.getTimeSheetEntry(id));
        verify(timeSheetRepo).findById(id);
    }
    
    
    @Test
    public void getEntriesBasedOnPaginationAndSorting_defaultParams() throws CustomBusinessException {
        int psId = 10;
        int expectedOffset = 0;
        int expectedPageSize = 20;
        Sort expectedSort = Sort.unsorted();

        Page<TimeSheetEntity> entityPage = new PageImpl<>(Arrays.asList(createTimeSheetEntityStub("Submitted")));
        when(timeSheetRepo.findByLsUserEntity_LsUserid(psId, PageRequest.of(expectedOffset, expectedPageSize, expectedSort))).thenReturn(entityPage);

        Page<TimeSheetEntity> result = service.getEntriesBasedOnUserId(psId, null, null, null);

        assertEquals(entityPage, result);
        verify(timeSheetRepo).findByLsUserEntity_LsUserid(psId, PageRequest.of(expectedOffset, expectedPageSize, expectedSort));
    }

    @Test
    public void getEntriesBasedOnPaginationAndSorting_customParams() throws CustomBusinessException {
        int psId = 10;
        int offset = 5;
        int pagesize = 10;
        String sortField = "fieldName";
        Sort expectedSort = Sort.by(Sort.Direction.ASC, sortField);

        Page<TimeSheetEntity> entityPage = new PageImpl<>(Arrays.asList(createTimeSheetEntityStub("Submitted")));
        when(timeSheetRepo.findByLsUserEntity_LsUserid(psId, PageRequest.of(offset, pagesize, expectedSort))).thenReturn(entityPage);

        Page<TimeSheetEntity> result = service.getEntriesBasedOnUserId(psId, offset, pagesize, sortField);

        assertEquals(entityPage, result);
        verify(timeSheetRepo).findByLsUserEntity_LsUserid(psId, PageRequest.of(offset, pagesize, expectedSort));
    }

    @Test
    public void getEntriesBasedOnPaginationAndSorting_largePageSize() throws CustomBusinessException {
        int psId = 10;
        int offset = 0;
        int largePageSize = 250; // Exceeds allowed limit

        assertThrows(CustomBusinessException.class, () -> service.getEntriesBasedOnUserId(psId, offset, largePageSize, null));
        verify(timeSheetRepo, never()).findByLsUserEntity_LsUserid(anyInt(), any());
    }
    
    private TimeSheetEntity createTimeSheetEntityStub(String lsStatus) {
		return TimeSheetEntity.builder().id(1).lsStatus(lsStatus).lsDescription("Worked On Project").
				lsInputChannel("QC").ldEntryDate(Date.valueOf("2024-04-10")).lsUserEntity(createUserEntityStub()).build();    	
    }
    
    
    private UserTimeSheetEntity createUserEntityStub() {
		return UserTimeSheetEntity.builder().lsUserid(1).lsUserName("Raj").build();    	
    }
    
    private TimeSheetDto createTimeSheetdtoStub(String lsStatus) {
    	return TimeSheetDto.builder().id(1).lsStatus(lsStatus).lsDescription("Worked On Project").
				lsInputChannel("QC").ldEntryDate(Date.valueOf("2024-04-10")).lsUserId("1").build();
  	}
    
  
    
    
}