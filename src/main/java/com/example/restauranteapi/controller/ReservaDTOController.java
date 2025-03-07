package com.example.restauranteapi.controller;

import com.example.restauranteapi.DTO.ReservaDTO;
import com.example.restauranteapi.DTO.ReservaDTO2;
import com.example.restauranteapi.model.Cliente;
import com.example.restauranteapi.model.Mesa;
import com.example.restauranteapi.repository.ClienteRepository;
import com.example.restauranteapi.repository.MesaRepository;
import com.example.restauranteapi.repository.ReservaRepository;
import com.example.restauranteapi.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservaDTOController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservaService reservaService;

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
                            .nombre(reserva.getCliente().getUsername())
                            .email(reserva.getCliente().getEmail())
                            .fecha(reserva.getHora().toLocalDate())
                            .numero_mesa(reserva.getMesa().getNumero())
                            .build()
            );

        });
        return ResponseEntity.ok(reservasDTO);

    }

    @GetMapping ("/reservasPorUsuario")
    public ResponseEntity<List<ReservaDTO2>> findAllByUser(@RequestParam("username") String username) {

        List<ReservaDTO2> reservasDTO = new ArrayList<>();
        Cliente cliente = clienteRepository.findByUsername(username).orElseThrow();


        reservaRepository.findByCliente(cliente).forEach(reserva -> {

            reservasDTO.add(
                    ReservaDTO2.builder()
                            .id(reserva.getId())
                            .hora(reserva.getHora().toLocalTime())
                            .fecha(reserva.getHora().toLocalDate())
                            .numero_mesa(reserva.getMesa().getNumero())
                            .personas(reserva.getPersonas())
                            .build()
            );

        });
        return ResponseEntity.ok(reservasDTO);

    }



    @GetMapping("/mesas-disponibles")
    public ResponseEntity<List<Mesa>> getMesasDisponibles(
            @RequestParam("fechaReserva")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaReserva) {

        List<Mesa> mesasDisponibles = reservaService.buscarReserva(fechaReserva);
        return ResponseEntity.ok(mesasDisponibles);
    }


}
