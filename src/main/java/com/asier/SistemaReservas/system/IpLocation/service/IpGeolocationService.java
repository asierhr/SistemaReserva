package com.asier.SistemaReservas.system.IpLocation.service;

import com.asier.SistemaReservas.system.IpLocation.domain.Location;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class IpGeolocationService {
    private final WebClient webClient;

    public IpGeolocationService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("https://ipapi.co").build();
    }

    public Mono<Location> getLocationByIp(String ipAddress){
        return webClient.get()
                .uri("/{ip}/json/", ipAddress)
                .retrieve()
                .bodyToMono(Location.class)
                .doOnError(e -> log.error("Error obteniendo geolocalización para IP: {}", ipAddress, e))
                .onErrorReturn(new Location());
    }

    public String extractIpFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
