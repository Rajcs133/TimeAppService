package com.microservice.timesheet.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.microservice.timesheet.Entity.TimeSheetEntity;


@Mapper
public interface TimeSheetMapper {
    TimeSheetMapper INSTANCE = Mappers.getMapper(TimeSheetMapper.class);
    
    @Mapping(target="lsUserId", source ="entity.lsUserEntity.lsUserid")
    @Mapping(target="lsUserName", source ="entity.lsUserEntity.lsUserName")
    TimeSheetDto toDto(TimeSheetEntity entity);
    
    @Mapping(target="lsUserEntity.lsUserid", source="dto.lsUserId")
    @Mapping(target="lsUserEntity.lsUserName", source="dto.lsUserName")
    TimeSheetEntity toEntity(TimeSheetDto dto);
}