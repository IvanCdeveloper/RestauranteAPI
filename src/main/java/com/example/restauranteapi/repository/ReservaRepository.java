package com.example.restauranteapi.repository;

import com.example.restauranteapi.model.Cliente;
import com.example.restauranteapi.model.Mesa;
import com.example.restauranteapi.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.mesa.id = :mesaId AND r.hora BETWEEN :inicio AND :fin")
    boolean existsByMesaIdAndHoraBetween(@Param("mesaId") Long mesaId, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    @Query("SELECT r FROM Reserva r WHERE DATE(r.hora) = :fecha")
    List<Reserva> findByFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT m FROM Mesa m WHERE m NOT IN (" +
            "SELECT r.mesa FROM Reserva r WHERE r.hora >= :inicio AND r.hora < :fin)")
    List<Mesa> findMesasDisponibles(@Param("inicio") LocalDateTime inicio,
                                    @Param("fin") LocalDateTime fin);


    List<Reserva> findByCliente(Cliente cliente);
}

