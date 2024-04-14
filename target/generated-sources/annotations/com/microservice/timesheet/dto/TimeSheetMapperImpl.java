package com.microservice.timesheet.dto;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.Entity.UserTimeSheetEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-14T21:33:00+0200",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.10 (Eclipse Adoptium)"
)
public class TimeSheetMapperImpl implements TimeSheetMapper {

    @Override
    public TimeSheetDto toDto(TimeSheetEntity entity) {
        if ( entity == null ) {
            return null;
        }

        TimeSheetDto.TimeSheetDtoBuilder timeSheetDto = TimeSheetDto.builder();

        Integer lsUserid = entityLsUserEntityLsUserid( entity );
        if ( lsUserid != null ) {
            timeSheetDto.lsUserId( String.valueOf( lsUserid ) );
        }
        timeSheetDto.lsUserName( entityLsUserEntityLsUserName( entity ) );
        timeSheetDto.id( entity.getId() );
        timeSheetDto.ldEntryDate( entity.getLdEntryDate() );
        timeSheetDto.lsDescription( entity.getLsDescription() );
        timeSheetDto.lsInputChannel( entity.getLsInputChannel() );
        timeSheetDto.lsStatus( entity.getLsStatus() );

        return timeSheetDto.build();
    }

    @Override
    public TimeSheetEntity toEntity(TimeSheetDto dto) {
        if ( dto == null ) {
            return null;
        }

        TimeSheetEntity.TimeSheetEntityBuilder timeSheetEntity = TimeSheetEntity.builder();

        timeSheetEntity.lsUserEntity( timeSheetDtoToUserTimeSheetEntity( dto ) );
        timeSheetEntity.id( dto.getId() );
        timeSheetEntity.ldEntryDate( dto.getLdEntryDate() );
        timeSheetEntity.lsDescription( dto.getLsDescription() );
        timeSheetEntity.lsInputChannel( dto.getLsInputChannel() );
        timeSheetEntity.lsStatus( dto.getLsStatus() );

        return timeSheetEntity.build();
    }

    private Integer entityLsUserEntityLsUserid(TimeSheetEntity timeSheetEntity) {
        UserTimeSheetEntity lsUserEntity = timeSheetEntity.getLsUserEntity();
        if ( lsUserEntity == null ) {
            return null;
        }
        return lsUserEntity.getLsUserid();
    }

    private String entityLsUserEntityLsUserName(TimeSheetEntity timeSheetEntity) {
        UserTimeSheetEntity lsUserEntity = timeSheetEntity.getLsUserEntity();
        if ( lsUserEntity == null ) {
            return null;
        }
        return lsUserEntity.getLsUserName();
    }

    protected UserTimeSheetEntity timeSheetDtoToUserTimeSheetEntity(TimeSheetDto timeSheetDto) {
        if ( timeSheetDto == null ) {
            return null;
        }

        UserTimeSheetEntity.UserTimeSheetEntityBuilder userTimeSheetEntity = UserTimeSheetEntity.builder();

        if ( timeSheetDto.getLsUserId() != null ) {
            userTimeSheetEntity.lsUserid( Integer.parseInt( timeSheetDto.getLsUserId() ) );
        }
        userTimeSheetEntity.lsUserName( timeSheetDto.getLsUserName() );

        return userTimeSheetEntity.build();
    }
}
