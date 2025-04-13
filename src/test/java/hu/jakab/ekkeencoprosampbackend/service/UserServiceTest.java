package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.user.UserDTO;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.UserMapper;
import hu.jakab.ekkeencoprosampbackend.model.Role;
import hu.jakab.ekkeencoprosampbackend.model.User;
import hu.jakab.ekkeencoprosampbackend.model.UserRoleAssignment;
import hu.jakab.ekkeencoprosampbackend.repository.RoleRepository;
import hu.jakab.ekkeencoprosampbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAll() {
        // Arrange
        List<User> users = List.of(new User());
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        // Act
        List<UserDTO> result = userService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(new UserDTO());

        // Act
        UserDTO result = userService.getById(userId.toString());

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetByIdNotFound() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getById(userId.toString()));
    }

    @Test
    void testSaveOrUpdateUser_NewUser() {
        // Arrange
        Authentication authentication = mock(JwtAuthenticationToken.class);
        User user = new User();
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        userService.saveOrUpdateUser(authentication);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSaveOrUpdateUser_UpdateExistingUser() {
        // Arrange
        Authentication authentication = mock(JwtAuthenticationToken.class);
        User existingUser = new User();
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        userService.saveOrUpdateUser(authentication);

        // Assert
        verify(userRepository, times(2)).save(any(User.class)); // Save called twice for roles and user
    }
}
