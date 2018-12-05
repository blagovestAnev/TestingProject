package com.endava.demo.service;

import com.endava.demo.dto.UserDto;
import java.util.Optional;

public interface UserService {

    /**
     * Creates or updates user.
     * Starts with comparing the provided object with those in the database. If match is found,
     * based on the login name, compares the password. If match again, the user is updated, if not, an exception is
     * thrown. If don't find such user, the system creates new one, giving him next id, hashing password and etc. In
     * both cases, an email is sent for confirmation of the successful task.
     *
     * @param  newUser  user DTO object with all the filled information
     * @return          user DTO object, after it is created or updated. If the password comparing existing user don't
     * match - exception is thrown.
     */
    UserDto createOrUpdate(UserDto newUser);

    /**
     * Finds certain object.
     * Returns object with all the necessary information from the database, based on the provided primary key, which in
     * the case is the login name.
     *
     * @param loginName This is the primary key element in the database, based on which the search in database is going
     *                  to be performed
     * @return          user DTO object
     */
    Optional<UserDto> find(String loginName);

    /**
     * Delete certain user.
     * Checks for user in the database, based on provided login name, which is the primary key. If match is found, the
     * user is deleted from the database.
     *
     * @param loginName This is the primary key element in the database, based on which the search in database is going
     *                  to be performed
     */
    void delete(String loginName);
}
