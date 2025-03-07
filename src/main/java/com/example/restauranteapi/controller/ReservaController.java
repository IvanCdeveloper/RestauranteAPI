package com.example.restauranteapi.controller;

import com.example.restauranteapi.DTO.ReservaDTO2;
import com.example.restauranteapi.model.Cliente;
import com.example.restauranteapi.model.Mesa;
import com.example.restauranteapi.model.Reserva;
import com.example.restauranteapi.repository.ClienteRepository;
import com.example.restauranteapi.repository.MesaRepository;
import com.example.restauranteapi.repository.ReservaRepository;
import com.example.restauranteapi.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
public class ReservaController {


    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ReservaService reservaService;

    @PostMapping ("/reservas")
    public ResponseEntity<?> crearReserva(@RequestBody Map<String, Object> requestBody) {
        try {
            // Extraer parámetros del JSON manualmente
            String clienteId = requestBody.get("cliente_id").toString();
            Long mesaId = Long.valueOf(requestBody.get("mesa_id").toString());
            Long numeroPersonas = Long.valueOf(requestBody.get("personas").toString());
            LocalDateTime fechaReserva = LocalDateTime.parse(requestBody.get("fecha").toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // Validar cliente y mesa
            Cliente cliente = clienteRepository.findByUsername(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            Mesa mesa = mesaRepository.findById(mesaId)
                    .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));



            Reserva reserva = reservaService.crearReservaPorNombre(clienteId, mesaId, numeroPersonas, fechaReserva);
            ReservaDTO2 reservaResponse = ReservaDTO2.builder()
                    .id(reserva.getId())
                    .hora(reserva.getHora().toLocalTime())
                    .fecha(reserva.getHora().toLocalDate())
                    .numero_mesa(reserva.getMesa().getNumero())
                    .personas(reserva.getPersonas())
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la reserva: " + e.getMessage());
        }
    }



    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {

        return reservaRepository.findById(id)
                .map(reserva -> {
                    reservaRepository.delete(reserva);
                    return ResponseEntity.noContent().<Void>build();  // 204 NOT CONTENT
                })
                .orElse(ResponseEntity.notFound().build());  // 404 NOT FOUND
    }




}
