package com.microservice.timesheet.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Builder
public class TimeSheetDto {
    
    private Integer id;
    private Date ldEntryDate;
    private String lsInputChannel;
    private String lsDescription;
    private String lsStatus;
    private String lsUserId;
    private String lsUserName;
    
    
    public TimeSheetDto(Integer id, Date entryDate, String inputChannel, String description,String status) {
        this.id = id;
        this.ldEntryDate = entryDate;
        this.lsInputChannel = inputChannel;
        this.lsDescription = description;
        this.lsStatus = status;
    }
}
