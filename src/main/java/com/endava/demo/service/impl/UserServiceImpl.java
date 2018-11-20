package com.endava.demo.service.impl;

import com.endava.demo.assembler.impl.UserAssembler;
import com.endava.demo.dto.UserDto;
import com.endava.demo.entity.User;
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
    private final UserAssembler userAssembler;
    private final int minNumber = 4;
    private final int maxNumber = 20;
    private final EmailService emailService;

    @Override
    @Transactional
    public UserDto createOrUpdate(UserDto newUser) {
        User dbUser = this.userRepository.findByLoginName(newUser.getLoginName());
        if (dbUser != null) {
            if (BCrypt.checkpw(newUser.getPassword(), dbUser.getPassword())) {
                newUser = this.userAssembler.toDto(this.userRepository.save(this.userAssembler.toEntity(newUser)));
                this.emailService.sendSimpleMessageUpdate(newUser.getEmail());
                return newUser;
            } else {
                throw new IllegalArgumentException("Not correct password.");
            }
        }
        newUser.setSalt(getRandomNumber(this.minNumber, this.maxNumber));
        newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(newUser.getSalt())));
        this.emailService.sendSimpleMessageCreate(newUser.getEmail());
        return this.userAssembler.toDto(this.userRepository.save(this.userAssembler.toEntity(newUser)));
    }

    @Override
    public Optional<UserDto> find(String loginName) {
        return this.userRepository.findById(loginName).map(this.userAssembler::toDto);
    }

    @Override
    public void delete(String loginName) {
        final Optional<User> user = this.userRepository.findById(loginName);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("There is no user with this id.");
        }
        this.userRepository.deleteById(loginName);
    }

    private int getRandomNumber(int minNumber, int maxNumber) {
        return (int) (Math.random() * ((maxNumber - minNumber) + 1)) + minNumber;
    }

}