package com.example.restauranteapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@Entity
@Data
@Table(name="reservas")
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Future(message = "No se permiten reservas en fechas pasadas")
    private LocalDateTime hora;

    private Long personas;

    @ManyToOne(targetEntity = Cliente.class)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(targetEntity = Mesa.class)
    @JoinColumn(name = "mesa_id")

    private Mesa mesa;


}
