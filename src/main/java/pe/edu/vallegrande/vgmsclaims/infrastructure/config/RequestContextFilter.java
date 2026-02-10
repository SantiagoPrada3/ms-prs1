package pe.edu.vallegrande.vgmsclaims.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Filtro para capturar y propagar el contexto de la solicitud a través del flujo reactivo.
 * Extrae información de la solicitud y la hace disponible en el contexto de Reactor.
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestContextFilter implements WebFilter {
    
    public static final String REQUEST_ID_KEY = "requestId";
    public static final String CORRELATION_ID_KEY = "correlationId";
    public static final String REQUEST_PATH_KEY = "requestPath";
    public static final String REQUEST_METHOD_KEY = "requestMethod";
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestId = exchange.getRequest().getId();
        String correlationId = exchange.getRequest().getHeaders()
                .getFirst("X-Correlation-ID");
        String requestPath = exchange.getRequest().getPath().value();
        String requestMethod = exchange.getRequest().getMethod().name();
        
        if (correlationId == null) {
            correlationId = requestId;
        }
        
        log.debug("Processing request: {} {} with correlationId: {}", 
                requestMethod, requestPath, correlationId);
        
        final String finalCorrelationId = correlationId;
        
        return chain.filter(exchange)
                .contextWrite(ctx -> ctx
                        .put(REQUEST_ID_KEY, requestId)
                        .put(CORRELATION_ID_KEY, finalCorrelationId)
                        .put(REQUEST_PATH_KEY, requestPath)
                        .put(REQUEST_METHOD_KEY, requestMethod));
    }
    
    /**
     * Obtiene el ID de correlación del contexto de Reactor
     */
    public static Mono<String> getCorrelationId() {
        return Mono.deferContextual(ctx -> 
                Mono.just(ctx.getOrDefault(CORRELATION_ID_KEY, "unknown")));
    }
    
    /**
     * Obtiene el ID de la solicitud del contexto de Reactor
     */
    public static Mono<String> getRequestId() {
        return Mono.deferContextual(ctx -> 
                Mono.just(ctx.getOrDefault(REQUEST_ID_KEY, "unknown")));
    }
}
