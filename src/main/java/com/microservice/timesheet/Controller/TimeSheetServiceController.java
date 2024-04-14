package com.microservice.timesheet.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.Services.Mode;
import com.microservice.timesheet.Services.Role;
import com.microservice.timesheet.Services.TimeSheetService;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping
@Tag(name = "TimeSheet", description = "Endpoints for managing time sheet entries")
public class TimeSheetServiceController {

	@Autowired
	private TimeSheetService loTimeSheetService;

	@Operation(summary = "Save a time sheet entry", description = "Endpoint to save a new time sheet entry.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully saved the time sheet entry.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TimeSheetDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request.") })
	@PostMapping(path = "/saveTimeSheetEntry")
	public ResponseEntity<TimeSheetDto> saveTimeSheetEntry(@RequestBody TimeSheetDto poTimeSheet)
			throws CustomBusinessException {
		poTimeSheet = loTimeSheetService.saveTimeSheetEntry(poTimeSheet);
		return new ResponseEntity<>(poTimeSheet, HttpStatus.OK);
	}

	@Operation(summary = "Retrieve a time sheet entry by ID", description = "Endpoint to retrieve a time sheet entry by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the time sheet entry.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TimeSheetDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Time sheet entry not found.") })
	@GetMapping(path = "/getEntryBasedOnTimeSheetId/{Id}")
	public ResponseEntity<TimeSheetDto> getEntryBasedOnTimeSheetId(@PathVariable("Id") int Id) throws Exception {
		TimeSheetDto poTimeSheetDto = loTimeSheetService.getEntryBasedOnTimeSheetId(Id);
		return new ResponseEntity<>(poTimeSheetDto, HttpStatus.OK);
	}

	@Operation(summary = "Retrieve time sheet entries based on user ID", description = "Endpoint to retrieve time sheet entries based on a user ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the time sheet entries.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request.") })
	@GetMapping(path = "/getEntriesBasedOnUserId/{lsUserId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<TimeSheetEntity>> getEntriesBasedOnUserId(
			@Parameter(description = "Offset for pagination") @RequestParam(name = "offset", required = false) Integer psOffset,
			@Parameter(description = "Size of each page") @RequestParam(name = "pagesize", required = false) Integer psPageSize,
			@Parameter(description = "Name of the field to sort by") @RequestParam(name = "fieldName", required = false) String psFieldName,
			@PathVariable("lsUserId") int lsUserId) throws Exception {
		return new ResponseEntity<Page<TimeSheetEntity>>(
				loTimeSheetService.getEntriesBasedOnUserId(lsUserId, psOffset, psPageSize, psFieldName), HttpStatus.OK);
	}

	@Operation(summary = "Submit time sheet entries", description = "Endpoint to submit time sheet entries for approval.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully submitted the time sheet entries.", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TimeSheetDto.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad request.") })
	@PostMapping(path = "/submitTimeSheetEntries")
	public ResponseEntity<TimeSheetDto[]> submitTimeSheetEntries(@RequestBody TimeSheetDto[] poTimeSheet)
			throws CustomBusinessException {
		poTimeSheet = loTimeSheetService.performTimeSheetTask(poTimeSheet, Role.EMPLOYEE.toString(),
				Mode.SUBMIT.toString());
		return new ResponseEntity<>(poTimeSheet, HttpStatus.OK);
	}

	@Operation(summary = "Approve time sheet entries", description = "Endpoint to approve time sheet entries.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully approved the time sheet entries.", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TimeSheetDto.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad request.") })
	@PostMapping(path = "/approveTimeSheetEntries")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<TimeSheetDto[]> approveTimeSheetEntries(@RequestBody TimeSheetDto[] poTimeSheet)
			throws CustomBusinessException {
		poTimeSheet = loTimeSheetService.performTimeSheetTask(poTimeSheet, Role.MANAGER.toString(),
				Mode.APPROVE.toString());
		return new ResponseEntity<>(poTimeSheet, HttpStatus.OK);
	}

	@Operation(summary = "Reject time sheet entries", description = "Endpoint to reject time sheet entries.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully rejected the time sheet entries.", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TimeSheetDto.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad request.") })
	@PostMapping(path = "/rejectTimeSheetEntries")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<TimeSheetDto[]> rejectTimeSheetEntries(@RequestBody TimeSheetDto[] poTimeSheet)
			throws CustomBusinessException {
		poTimeSheet = loTimeSheetService.performTimeSheetTask(poTimeSheet, Role.MANAGER.toString(),
				Mode.REJECT.toString());
		return new ResponseEntity<>(poTimeSheet, HttpStatus.OK);
	}
}
