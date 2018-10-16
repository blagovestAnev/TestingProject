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

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    @Override
    @Transactional
    public UserDto createOrUpdate(UserDto newUser) {
        newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(4)));
        return userAssembler.toDto(userRepository.save(userRepository.findById(newUser.getId()).orElse(userRepository.save(userAssembler.toEntity(newUser)))));
    }

    @Override
    public Optional<UserDto> find(Long id) {
        return userRepository.findById(id).map(userAssembler::toDto);
    }

    @Override
    public void delete(Long id) {
        final Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("There is no user with this id.");
        }
        userRepository.deleteById(id);
    }
}