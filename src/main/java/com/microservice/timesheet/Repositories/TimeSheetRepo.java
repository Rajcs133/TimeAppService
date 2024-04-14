package com.microservice.timesheet.Repositories;



import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.timesheet.Entity.TimeSheetEntity;


public interface TimeSheetRepo extends JpaRepository<TimeSheetEntity, Integer>{
	
	public Optional<TimeSheetEntity> findById(Integer Id);

	public Page<TimeSheetEntity> findByLsUserEntity_LsUserid(Integer psId, PageRequest pageable);
	
	

}
