package com.example.restauranteapi.controller;

import com.example.restauranteapi.DTO.ReservaDTO;
import com.example.restauranteapi.repository.ClienteRepository;
import com.example.restauranteapi.repository.MesaRepository;
import com.example.restauranteapi.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservaDTOController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @GetMapping ("/reservas")
    public ResponseEntity<List<ReservaDTO>> findAllByDate(@RequestParam("fecha") @Valid LocalDate date) {

        List<ReservaDTO> reservasDTO = new ArrayList<>();

        reservaRepository.findByFecha(date).forEach(reserva -> {

            reservasDTO.add(
                    ReservaDTO.builder()
                            .nombre(reserva.getCliente().getNombre())
                            .email(reserva.getCliente().getEmail())
                            .fecha(reserva.getHora().toLocalDate())
                            .numero_mesa(reserva.getMesa().getNumero())
                            .build()
            );

        });
        return ResponseEntity.ok(reservasDTO);

    }
}
