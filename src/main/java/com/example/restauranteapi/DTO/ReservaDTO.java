package com.example.restauranteapi.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReservaDTO {
    private String nombre;
    private String email;
    private LocalDate fecha;
    private Long numero_mesa;

}
