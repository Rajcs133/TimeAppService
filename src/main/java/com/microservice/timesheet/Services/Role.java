package com.microservice.timesheet.Services;

public enum Role {
    MANAGER,
    EMPLOYEE,
    ADMIN;


    @Override
    public String toString() {
        return name();
    }
}
