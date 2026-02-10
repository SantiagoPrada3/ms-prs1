package pe.edu.vallegrande.vgmsclaims.infrastructure.adapters.in.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import pe.edu.vallegrande.vgmsclaims.application.dto.common.ApiResponse;
import pe.edu.vallegrande.vgmsclaims.application.dto.common.ErrorMessage;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.BusinessRuleException;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.ConflictException;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.NotFoundException;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific.ComplaintAlreadyClosedException;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific.ComplaintNotFoundException;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific.IncidentAlreadyResolvedException;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific.IncidentNotFoundException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * Manejador global de excepciones
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleNotFoundException(
            NotFoundException ex, ServerWebExchange exchange) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        ErrorMessage error = ErrorMessage.of(ex.getCode(), ex.getMessage(), 
                exchange.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(error)));
    }
    
    @ExceptionHandler(BusinessRuleException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleBusinessRuleException(
            BusinessRuleException ex, ServerWebExchange exchange) {
        log.warn("Violación de regla de negocio: {}", ex.getMessage());
        ErrorMessage error = ErrorMessage.of(ex.getCode(), ex.getMessage(),
                exchange.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(error)));
    }
    
    @ExceptionHandler(ConflictException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleConflictException(
            ConflictException ex, ServerWebExchange exchange) {
        log.warn("Conflicto de estado: {}", ex.getMessage());
        ErrorMessage error = ErrorMessage.of(ex.getCode(), ex.getMessage(),
                exchange.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(error)));
    }
    
    @ExceptionHandler(ComplaintNotFoundException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleComplaintNotFoundException(
            ComplaintNotFoundException ex, ServerWebExchange exchange) {
        log.warn("Queja no encontrada: {}", ex.getMessage());
        ErrorMessage error = ErrorMessage.of(ex.getCode(), ex.getMessage(),
                exchange.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(error)));
    }
    
    @ExceptionHandler(IncidentNotFoundException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleIncidentNotFoundException(
            IncidentNotFoundException ex, ServerWebExchange exchange) {
        log.warn("Incidente no encontrado: {}", ex.getMessage());
        ErrorMessage error = ErrorMessage.of(ex.getCode(), ex.getMessage(),
                exchange.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(error)));
    }
    
    @ExceptionHandler(ComplaintAlreadyClosedException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleComplaintAlreadyClosedException(
            ComplaintAlreadyClosedException ex, ServerWebExchange exchange) {
        log.warn("Queja ya cerrada: {}", ex.getMessage());
        ErrorMessage error = ErrorMessage.of(ex.getCode(), ex.getMessage(),
                exchange.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(error)));
    }
    
    @ExceptionHandler(IncidentAlreadyResolvedException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleIncidentAlreadyResolvedException(
            IncidentAlreadyResolvedException ex, ServerWebExchange exchange) {
        log.warn("Incidente ya resuelto: {}", ex.getMessage());
        ErrorMessage error = ErrorMessage.of(ex.getCode(), ex.getMessage(),
                exchange.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(error)));
    }
    
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleValidationException(
            WebExchangeBindException ex, ServerWebExchange exchange) {
        log.warn("Error de validación: {}", ex.getMessage());
        
        var fieldErrors = ex.getFieldErrors().stream()
                .map(error -> ErrorMessage.FieldError.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .rejectedValue(error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
        
        ErrorMessage error = ErrorMessage.builder()
                .code("VALIDATION_ERROR")
                .message("Error de validación en los datos enviados")
                .path(exchange.getRequest().getPath().value())
                .fieldErrors(fieldErrors)
                .build();
        
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(error)));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleIllegalArgumentException(
            IllegalArgumentException ex, ServerWebExchange exchange) {
        log.warn("Argumento inválido: {}", ex.getMessage());
        ErrorMessage error = ErrorMessage.of("INVALID_ARGUMENT", ex.getMessage(),
                exchange.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(error)));
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleIllegalStateException(
            IllegalStateException ex, ServerWebExchange exchange) {
        log.warn("Estado inválido: {}", ex.getMessage());
        ErrorMessage error = ErrorMessage.of("INVALID_STATE", ex.getMessage(),
                exchange.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(error)));
    }
    
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleGenericException(
            Exception ex, ServerWebExchange exchange) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);
        ErrorMessage error = ErrorMessage.of("INTERNAL_ERROR", 
                "Error interno del servidor. Por favor, intente más tarde.",
                exchange.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(error)));
    }
}
