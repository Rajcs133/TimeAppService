package com.microservice.timesheet.Services;

public enum Mode {
	INSERT, DELETE, APPROVE, RETRIEVE,SUBMIT, REJECT,EMPLOYEE,ADMIN,MANAGER;

	@Override
	public String toString() {
		return name();
	}

}
