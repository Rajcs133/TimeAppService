package com.microservice.timesheet.Services;

/**
 * Interface representing a factory for creating user objects.
 * 
 * This interface defines a method to get a user object based on the provided role.
 */
public interface UserFactory {
    
    /**
     * Retrieves a user object based on the provided role.
     * 
     * @param role The role of the user for which the object needs to be retrieved.
     * @return AbstractUserActivity The user object corresponding to the provided role.
     */
    public AbstractUserActivity getUserObj(String role);

}
