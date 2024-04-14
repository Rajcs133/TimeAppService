package com.microservice.timesheet.Services;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Implementation class for the UserFactory interface.
 * 
 * This class provides the concrete implementation of the UserFactory interface
 * using a map to store and retrieve user objects based on their roles.
 */
@Service
@RequiredArgsConstructor
public class UserFactoryImpl implements UserFactory {

    /**
     * A map containing user objects mapped by their respective roles.
     */
    public final Map<String, AbstractUserActivity> allUserObj;

    /**
     * Retrieves a user object based on the provided role.
     * 
     * @param psRole The role of the user for which the object needs to be retrieved.
     * @return AbstractUserActivity The user object corresponding to the provided role.
     */
    @Override
    public AbstractUserActivity getUserObj(String psRole) {
        return allUserObj.get(psRole);
    }

}
