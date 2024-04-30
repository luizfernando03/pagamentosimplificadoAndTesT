package com.pagamentosimplificado;

import com.pagamentosimplificado.domain.user.User;
import com.pagamentosimplificado.domain.user.UserType;
import com.pagamentosimplificado.dtos.UserDTO;
import com.pagamentosimplificado.repositories.UserRepository;
import com.pagamentosimplificado.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pagamentosimplificado.domain.user.UserType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateTransaction_InsufficientBalance_ThrowsException() {
        // Arrange
        User sender = new User();
        sender.setUserType(CUSTOMER);
        sender.setBalance(BigDecimal.TEN);

        BigDecimal amount = BigDecimal.valueOf(20);

        // Act & Assert
        assertThrows(Exception.class, () -> userService.validateTransaction(sender, amount));
    }

    @Test
    void validateTransaction_MerchantUser_ThrowsException() {
        // Arrange
        User sender = new User();
        sender.setUserType(MERCHANT);

        BigDecimal amount = BigDecimal.TEN;

        // Act & Assert
        assertThrows(Exception.class, () -> userService.validateTransaction(sender, amount));
    }

    @Test
    void findUserById_UserExists_ReturnsUser() throws Exception {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findUserById(userId);

        // Assert
        assertEquals(user, foundUser);
    }

    @Test
    void findUserById_UserDoesNotExist_ThrowsException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> userService.findUserById(userId));
    }



    @Test
    void getAllUsers_ReturnsListOfUsers() {
        // Arrange
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> returnedList = userService.getAllUsers();

        // Assert
        assertEquals(userList, returnedList);
        verify(userRepository, times(1)).findAll();
    }
}
