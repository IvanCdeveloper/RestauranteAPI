package com.example.restauranteapi.DTO;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ReservaDTO2 {

    private Long id;
    private Long personas;
    private Long numero_mesa;
    private LocalDate fecha;
    private LocalTime hora;
}
