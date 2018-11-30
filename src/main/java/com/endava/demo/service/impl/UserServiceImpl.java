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
    private final int minNumber = 4;
    private final int maxNumber = 20;
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
        newUser.setId(getNewIdNumber());
        newUser.setSalt(getRandomNumber(this.minNumber, this.maxNumber));
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
            throw new IllegalArgumentException("There is no user with this id.");
        }
        this.emailService.sendSimpleMessageDelete(user.map(User::getEmail).orElse(null));
        this.userRepository.deleteById(loginName);
    }

    private Long getNewIdNumber() {
        Long maxNumber = this.userRepository.findLastUserId();
        if (maxNumber == null) {
            return 1L;
        }
        return maxNumber + 1;
    }

    private int getRandomNumber(int minNumber, int maxNumber) {
        return (int) (Math.random() * ((maxNumber - minNumber) + 1)) + minNumber;
    }
}