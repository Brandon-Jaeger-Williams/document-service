package com.assessment.documentservice.security;

import com.assessment.documentservice.core.security.AuthUserDetailsService;
import com.assessment.documentservice.core.security.AuthUserDetailsServiceImpl;
import com.assessment.documentservice.entity.UserEntity;
import com.assessment.documentservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthUserDetailsServiceImplTest {

    private AuthUserDetailsService authUserDetailsService;

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    @BeforeEach
    public void setUp() {
        authUserDetailsService = new AuthUserDetailsServiceImpl(userRepository);
    }

    @Test
    void test_loadUserByUsername_with_valid_user_should_return_user() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntity.setPassword("123");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = authUserDetailsService.loadUserByUsername("test");
        assertNotNull(userDetails);
    }

    @Test
    void test_loadUserByUsername_with_in_valid_user_should_throw_an_exception() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> authUserDetailsService.loadUserByUsername("test"));

        assertEquals("User not found with username: test", exception.getMessage());
    }
}