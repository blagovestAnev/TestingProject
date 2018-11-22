package com.endava.demo.repository;

import com.endava.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Query("select user " +
            "from User user " +
            "where user.loginName=:loginName ")
    User findByLoginName(@Param("loginName") String loginName);

    @Query("select MAX(user.id) " +
            "from User user ")
    Long findLastUserId();

}
