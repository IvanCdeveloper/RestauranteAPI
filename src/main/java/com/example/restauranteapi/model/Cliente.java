package com.example.restauranteapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Data
@Table(name="clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres") // Validación de longitud mínima
    private String nombre;
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    private String email;
    private String telefono;

    @OneToMany(targetEntity = Reserva.class, cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Reserva> reservas = new ArrayList<>();


}
