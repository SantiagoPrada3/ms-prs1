package pe.edu.vallegrande.vgmsclaims.domain.ports.out;

import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Puerto de salida para el cliente del servicio de infraestructura
 */
public interface IInfrastructureClient {
    
    /**
     * Obtiene información de una zona por su ID
     * @param zoneId identificador de la zona
     * @return datos de la zona
     */
    Mono<Map<String, Object>> getZoneById(String zoneId);
    
    /**
     * Obtiene información de una caja de agua por su ID
     * @param waterBoxId identificador de la caja de agua
     * @return datos de la caja de agua
     */
    Mono<Map<String, Object>> getWaterBoxById(String waterBoxId);
    
    /**
     * Verifica si una zona existe
     * @param zoneId identificador de la zona
     * @return true si existe
     */
    Mono<Boolean> existsZone(String zoneId);
    
    /**
     * Verifica si una caja de agua existe
     * @param waterBoxId identificador de la caja de agua
     * @return true si existe
     */
    Mono<Boolean> existsWaterBox(String waterBoxId);
    
    /**
     * Obtiene cajas de agua de una zona
     * @param zoneId identificador de la zona
     * @return lista de cajas de agua
     */
    Mono<java.util.List<Map<String, Object>>> getWaterBoxesByZone(String zoneId);
}
