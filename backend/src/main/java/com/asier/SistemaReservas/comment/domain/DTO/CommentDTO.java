package com.asier.SistemaReservas.comment.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private String comment;
    private Integer rating;
    private String userName;
    private Integer hotelId;
}
