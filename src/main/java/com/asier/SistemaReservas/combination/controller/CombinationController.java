package com.asier.SistemaReservas.combination.controller;

import com.asier.SistemaReservas.combination.domain.dto.CombinationDTO;
import com.asier.SistemaReservas.combination.service.CombinationService;
import com.asier.SistemaReservas.search.flightSearch.domain.dto.FlightSearchDTO;
import com.asier.SistemaReservas.system.IpLocation.service.IpGeolocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CombinationController {
    private final CombinationService combinationService;
    private final IpGeolocationService ipGeolocationService;

    @GetMapping(path = "/combination/search")
    public CombinationDTO getCombinationBySearch(@RequestBody FlightSearchDTO flightSearchDTO, HttpServletRequest request){
        String ipAddress = ipGeolocationService.extractIpFromRequest(request);
        return combinationService.getCombinationBySearch(flightSearchDTO, ipAddress);
    }
}
