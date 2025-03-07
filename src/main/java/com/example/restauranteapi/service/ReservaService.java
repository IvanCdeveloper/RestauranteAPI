package com.example.restauranteapi.service;


import com.example.restauranteapi.model.Cliente;
import com.example.restauranteapi.model.Mesa;
import com.example.restauranteapi.model.Reserva;
import com.example.restauranteapi.repository.ClienteRepository;
import com.example.restauranteapi.repository.MesaRepository;
import com.example.restauranteapi.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Reserva crearReserva(Long clienteId, Long mesaId, Long numeroPersonas, LocalDateTime fechaReserva) {
        // Validar cliente y mesa
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        // Verificar si ya hay una reserva en la misma mesa en el rango de una hora
        LocalDateTime finReserva = fechaReserva.plusHours(1);
        boolean mesaReservada = reservaRepository.existsByMesaIdAndHoraBetween(mesaId, fechaReserva, finReserva);

        if (mesaReservada) {
            throw new IllegalStateException("Ya hay una reserva en esta mesa en el rango de una hora.");
        }

        // Crear nueva reserva usando el builder
        Reserva reserva = Reserva.builder()
                .cliente(cliente)
                .mesa(mesa)
                .hora(fechaReserva)
                .personas(numeroPersonas)
                .build();

        // Guardar reserva en la base de datos
        return reservaRepository.save(reserva);
    }
    public Reserva crearReservaPorNombre(String clienteId, Long mesaId, Long numeroPersonas, LocalDateTime fechaReserva) {
        // Validar cliente y mesa
        Cliente cliente = clienteRepository.findByUsername(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        // Verificar si ya hay una reserva en la misma mesa en el rango de una hora
        LocalDateTime finReserva = fechaReserva.plusHours(1);
        boolean mesaReservada = reservaRepository.existsByMesaIdAndHoraBetween(mesaId, fechaReserva, finReserva);

        if (mesaReservada) {
            throw new IllegalStateException("Ya hay una reserva en esta mesa en el rango de una hora.");
        }

        // Crear nueva reserva usando el builder
        Reserva reserva = Reserva.builder()
                .cliente(cliente)
                .mesa(mesa)
                .hora(fechaReserva)
                .personas(numeroPersonas)
                .build();

        // Guardar reserva en la base de datos
        return reservaRepository.save(reserva);
    }

    public List<Mesa> buscarReserva(LocalDateTime fechaReserva) {



        LocalDateTime finReserva = fechaReserva.plusHours(2);

        List<Mesa> mesasDisponibles = reservaRepository.findMesasDisponibles(fechaReserva, finReserva);




        return mesasDisponibles;
    }
}


