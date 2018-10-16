package com.endava.demo.service;

import com.endava.demo.controller.UserController;
import com.endava.demo.dto.UserDto;
import com.endava.demo.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import com.google.common.collect.ImmutableSet;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserServiceTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Long userId = 5L;
    private final String pathFind = "/user/find/";
    private final String pathDelete = "/user/delete/";
    private final String pathCreateOrUpdate = "/user/";
    private final ImmutableSet set = ImmutableSet.of(1L, 2L, 3L, 4L, 5L);
    private final UserDto userDto = UserDto.builder()
            .id(userId)
            .loginName("testing")
            .password("pass")
            .firstName("ivan")
            .lastName("ivanov")
            .role("role")
            .build();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserServiceImpl mockUserServiceImpl;

    @Test
    public void noContentWhenDontFindUser() throws Exception {
        when(this.mockUserServiceImpl.find(any())).thenReturn(Optional.empty());
        this.mockMvc.perform(get(pathFind + userId)).andExpect(status().isNoContent());
    }

    @Test
    public void okWhenFindUser() throws Exception {
        when(this.mockUserServiceImpl.find(userId)).thenReturn(Optional.ofNullable(userDto));
        this.mockMvc.perform(get(pathFind + userId)).andExpect(status().isOk());
    }

    @Test
    public void okWhenFindUserAndDelete() throws Exception {
        assertTrue(set.contains(userId));
        this.mockMvc.perform(delete(pathDelete + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void okWhenCreateUser() throws Exception {
        when(this.mockUserServiceImpl.createOrUpdate(this.userDto)).thenReturn(this.userDto);
        this.mockMvc.perform(put(this.pathCreateOrUpdate).content(mapper.writeValueAsBytes(this.userDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().bytes(mapper.writeValueAsBytes(this.userDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void okWhenUpdateUser() throws Exception {
        UserDto userDto2 = UserDto.builder()
                .id(this.userDto.getId())
                .loginName("some")
                .password("newPass")
                .firstName("fakeName")
                .lastName("againFakeName")
                .role("fakeRole")
                .build();
        when(this.mockUserServiceImpl.createOrUpdate(this.userDto)).thenReturn(userDto2);
        this.mockMvc.perform(put(this.pathCreateOrUpdate).content(this.mapper.writeValueAsBytes(this.userDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().bytes(this.mapper.writeValueAsBytes(userDto2)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
    }
}
