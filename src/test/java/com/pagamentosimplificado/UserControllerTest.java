package com.pagamentosimplificado;

import com.pagamentosimplificado.controllers.TransactionController;
import com.pagamentosimplificado.controllers.UserController;
import com.pagamentosimplificado.domain.transaction.Transaction;
import com.pagamentosimplificado.dtos.TransactionDTO;
import com.pagamentosimplificado.domain.user.User;
import com.pagamentosimplificado.dtos.UserDTO;
import com.pagamentosimplificado.domain.user.UserType;
import com.pagamentosimplificado.services.TransactionService;
import com.pagamentosimplificado.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ControllerTest {

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private UserService userService;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser() {
        // Given
        UserDTO userDTO = new UserDTO("John", "Doe", "123456789", BigDecimal.ZERO, "john.doe@example.com", "password", UserType.CUSTOMER);
        User user = new User(1L, "John", "Doe", "123456789", "john.doe@example.com", BigDecimal.ZERO, "password", UserType.CUSTOMER);
        when(userService.createUser(new UserDTO(user.getFirstName(), user.getLastName(), user.getDocument(), user.getBalance(), user.getEmail(), user.getPassword(), user.getUserType()))).thenReturn(user);

        // When
        ResponseEntity<User> responseEntity = userController.createUser(userDTO);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void testGetAllUsers() {
        // Given
        User user1 = new User(1L, "John", "Doe", "123456789", "john.doe@example.com", BigDecimal.ZERO, "password", UserType.CUSTOMER);
        User user2 = new User(2L, "Jane", "Smith", "987654321", "jane.smith@example.com", BigDecimal.ZERO, "password", UserType.MERCHANT);
        List<User> users = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(users);

        // When
        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());
    }

    @Test
    void testCreateTransaction() throws Exception {
        // Given
        User sender = new User(1L, "John", "Doe", "123456789", "john.doe@example.com", BigDecimal.ZERO, "password", UserType.CUSTOMER);
        User receiver = new User(2L, "Jane", "Smith", "987654321", "jane.smith@example.com", BigDecimal.ZERO, "password", UserType.MERCHANT);
        TransactionDTO transactionDTO = new TransactionDTO(BigDecimal.TEN, sender.getId(), receiver.getId());
        Transaction transaction = new Transaction(1L, BigDecimal.TEN, sender, receiver, LocalDateTime.now());
        when(transactionService.createTransaction(transactionDTO)).thenReturn(transaction);

        // When
        ResponseEntity<Transaction> responseEntity = null;
        try {
            responseEntity = transactionController.createTransaction(transactionDTO);
        } catch (Exception e) {

        }

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transaction, responseEntity.getBody());
    }

}
