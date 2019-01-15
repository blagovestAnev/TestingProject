package com.endava.demo.service.impl;

import com.endava.demo.dto.UserDto;
import com.endava.demo.entity.User;
import com.endava.demo.mapper.UserMapper;
import com.endava.demo.repository.UserRepository;
import com.endava.demo.service.EmailService;
import com.endava.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final int MIN_NUMBER = 4;
    private static final int MAX_NUMBER = 20;
    private final EmailService emailService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createOrUpdate(UserDto newUser) {
        User dbUser = this.userRepository.findByLoginName(newUser.getLoginName());
        if (dbUser != null) {
            newUser.setId(dbUser.getId());
            newUser.setSalt(dbUser.getSalt());
            if (BCrypt.checkpw(newUser.getPassword(), dbUser.getPassword())) {
                newUser.setPassword(dbUser.getPassword());
                this.emailService.sendSimpleMessageUpdate(newUser.getEmail());
                return this.userMapper.toDto(this.userRepository.save(this.userMapper.toEntity(newUser)));
            } else {
                throw new IllegalArgumentException("Not correct password.");
            }
        }
        newUser.setId(getNextIdNumber());
        newUser.setSalt(getRandomNumber());
        newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(newUser.getSalt())));
        this.emailService.sendSimpleMessageCreate(newUser.getEmail());
        return this.userMapper.toDto(this.userRepository.save(this.userMapper.toEntity(newUser)));
    }


    @Override
    public Optional<UserDto> find(String loginName) {
        return this.userRepository.findById(loginName).map(this.userMapper::toDto);
    }

    @Override
    public void delete(String loginName) {
        final Optional<User> user = this.userRepository.findById(loginName);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("There is no user with this login name.");
        }
        this.emailService.sendSimpleMessageDelete(user.map(User::getEmail).orElse(null));
        this.userRepository.deleteById(loginName);
    }

    /**
     * Finds the next id number in the database.
     * Returns the next number in the database from the user table, checking for any user existence. It finds the
     * highest user id number and adds 1 to his number to get the next available. If don't find any user, returns just
     * 1, which means that the database is empty and this user will be the first one.
     *
     * @return the next id number or just 1
     */
    private Long getNextIdNumber() {
        Long maxNumber = this.userRepository.findLastUserId();
        if (maxNumber == null) {
            return 1L;
        }
        return maxNumber + 1;
    }

    /**
     * Returns random number between minimum (can't be less than 4, because of salt specifications) and maximum
     * (can't be higher than 31, because of salt specifications - recommended until 20,
     * because password comparison becomes too slow), used for the salt in BCrypt.
     *
     * @return random integer number
     */
    private int getRandomNumber() {
        return (int) (Math.random() * ((MAX_NUMBER - MIN_NUMBER) + 1)) + MIN_NUMBER;
    }
}