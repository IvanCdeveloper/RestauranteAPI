package com.example.restauranteapi.controller;


import com.example.restauranteapi.model.Mesa;
import com.example.restauranteapi.repository.ClienteRepository;
import com.example.restauranteapi.repository.MesaRepository;
import com.example.restauranteapi.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MesaController {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/mesas")
    public ResponseEntity<List<Mesa>> getMesas(){

        List<Mesa> mesas = mesaRepository.findAll();
        return new ResponseEntity<>(mesas, HttpStatus.OK);

    }


    @PostMapping("/mesas")
    public ResponseEntity<Mesa> crearMesa(@RequestBody @Valid Mesa mesa){
        var Mesa = mesaRepository.save(mesa);
        return new ResponseEntity<>(Mesa, HttpStatus.CREATED);     // 201 CREATED
        // return ResponseEntity.status(HttpStatus.CREATED).body(Mesa);
    }

    @GetMapping("/mesas/{id}")
    public ResponseEntity<Mesa> getMesa(@PathVariable Long id){
        return mesaRepository.findById(id)
                .map(mesa -> ResponseEntity.ok().body(mesa)) //200 OK
                .orElse(ResponseEntity.notFound().build());         // 404 NOT FOUND
    }

    @PutMapping("/mesas/{id}")
    public ResponseEntity<?> updateMesa(@PathVariable Long id, @RequestBody @Valid Mesa nuevoMesa){
        return mesaRepository.findById(id)
                .map(mesa -> {
                    mesa.setNumero(nuevoMesa.getNumero());
                    mesa.setDescripcion(nuevoMesa.getDescripcion());
                    mesaRepository.save(mesa);
                    return ResponseEntity.ok().body(mesa);  //200  OK
                }).orElseGet(() -> {
                    return ResponseEntity.ok().body(nuevoMesa);
                });

    }

    @DeleteMapping("/mesas/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) {

        return mesaRepository.findById(id)
                .map(mesa -> {
                    mesaRepository.delete(mesa);
                    return ResponseEntity.noContent().<Void>build();  // 204 NOT CONTENT
                })
                .orElse(ResponseEntity.notFound().build());  // 404 NOT FOUND
    }
}
