package com.asier.SistemaReservas.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelFavouriteDTO {
    private Long id;
    private List<HotelSummaryDTO> hotels;
    private UserDTO user;
}
