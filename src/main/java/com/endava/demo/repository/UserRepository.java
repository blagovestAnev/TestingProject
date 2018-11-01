package com.endava.demo.repository;

import com.endava.demo.dto.UserDto;
import com.endava.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select user " +
            "from User user " +
            "where user.id=:number ")
    User findByIdNumber(@Param("number") Long number);

}
