package com.pagamentosimplificado;

import com.pagamentosimplificado.Infra.ControllerExceptionHandler;
import com.pagamentosimplificado.dtos.ExceptionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ControllerExceptionHandlerTest {

    private ControllerExceptionHandler controllerExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controllerExceptionHandler = new ControllerExceptionHandler();
    }

    @Test
    void testHandleDataIntegrityViolationException() {
        // Given
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Usuário já cadastrado");

        // When
        ResponseEntity responseEntity = controllerExceptionHandler.threatDuplicateEntry(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ExceptionDTO exceptionDTO = (ExceptionDTO) responseEntity.getBody();
        assertEquals("Usuário já cadastrado", exceptionDTO.message());
        assertEquals("400", exceptionDTO.statusCode());
    }



}
