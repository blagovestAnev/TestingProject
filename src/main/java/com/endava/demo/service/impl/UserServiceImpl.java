package com.endava.demo.service.impl;

import com.endava.demo.assembler.impl.UserAssembler;
import com.endava.demo.dto.UserDto;
import com.endava.demo.entity.User;
import com.endava.demo.repository.UserRepository;
import com.endava.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;


import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;
    private int random = ThreadLocalRandom.current().nextInt(4, 20);
    private int randomNumber = random;

    @Override
    @Transactional
    public UserDto createOrUpdate(UserDto newUser) {

        User dbUser = this.userRepository.findByIdNumber(newUser.getId());
        if (dbUser != null && newUser.getId().equals(dbUser.getId())) {
            if (newUser.getLoginName().equals(dbUser.getLoginName()) && BCrypt.checkpw(newUser.getPassword(), dbUser.getPassword())) {
                newUser.setPassword(dbUser.getPassword());
                newUser.setSalt(dbUser.getSalt());
                newUser = this.userAssembler.toDto(this.userRepository.save(this.userAssembler.toEntity(newUser)));
                return newUser;
            } else {
                throw new IllegalArgumentException("Not correct username or password.");
            }
        }

        newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(this.randomNumber)));
        newUser.setSalt(this.randomNumber);
        return this.userAssembler.toDto(this.userRepository.save(this.userAssembler.toEntity(newUser)));
    }

    @Override
    public Optional<UserDto> find(Long id) {
        return this.userRepository.findById(id).map(this.userAssembler::toDto);
    }

    @Override
    public void delete(Long id) {
        final Optional<User> user = this.userRepository.findById(id);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("There is no user with this id.");
        }
        this.userRepository.deleteById(id);
    }
}