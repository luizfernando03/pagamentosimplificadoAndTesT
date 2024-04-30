package com.pagamentosimplificado;

import com.pagamentosimplificado.domain.transaction.Transaction;
import com.pagamentosimplificado.domain.user.User;
import com.pagamentosimplificado.domain.user.UserType;
import com.pagamentosimplificado.dtos.UserDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Domain {
    @Nested
    class UserTest {

        @Test
        void testUserConstructorWithDTO() {
            // Given
            UserDTO userDTO = new UserDTO("John", "Doe", "123456789", BigDecimal.ZERO, "john.doe@example.com", "password", UserType.CUSTOMER);

            // When
            User user = new User(userDTO);

            // Then
            assertEquals(userDTO.firstName(), user.getFirstName());
            assertEquals(userDTO.lastName(), user.getLastName());
            assertEquals(userDTO.document(), user.getDocument());
            assertEquals(userDTO.email(), user.getEmail());
            assertEquals(userDTO.balance(), user.getBalance());
            assertEquals(userDTO.password(), user.getPassword());
            assertEquals(userDTO.userType(), user.getUserType());
        }
    }

    @Nested
    class TransactionTest {

        @Test
        void testTransactionConstructor() {
            // Given
            long id = 1L;
            BigDecimal amount = new BigDecimal("100.00");
            User sender = new User();
            User receiver = new User();
            LocalDateTime timestamp = LocalDateTime.now();

            // When
            Transaction transaction = new Transaction(id, amount, sender, receiver, timestamp);

            // Then
            assertEquals(id, transaction.getId());
            assertEquals(amount, transaction.getAmount());
            assertEquals(sender, transaction.getSender());
            assertEquals(receiver, transaction.getReceiver());
            assertEquals(timestamp, transaction.getTimestamp());
        }
    }
}
