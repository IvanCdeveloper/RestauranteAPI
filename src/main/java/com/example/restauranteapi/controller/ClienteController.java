package com.example.restauranteapi.controller;


import com.example.restauranteapi.model.Cliente;
import com.example.restauranteapi.repository.ClienteRepository;
import com.example.restauranteapi.repository.MesaRepository;
import com.example.restauranteapi.repository.ReservaRepository;
import com.example.restauranteapi.service.ClienteService;
import com.example.restauranteapi.utils.PaginationLinksUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PaginationLinksUtils paginationLinksUtils;

    @GetMapping("/clientes")
    public ResponseEntity<Page<Cliente>> getClientes(@PageableDefault(page = 0, size = 5) Pageable pageable, HttpServletRequest request) {

        Page<Cliente> clientes = clienteService.findAllPageable(pageable);


        UriComponentsBuilder uriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequest();

        return ResponseEntity.ok().header("link",
                paginationLinksUtils.createLinkHeader(clientes, uriComponentsBuilder)).body(clientes);

    }


    @PostMapping("/clientes")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente){
        var Cliente = clienteRepository.save(cliente);
        return new ResponseEntity<>(Cliente, HttpStatus.CREATED);     // 201 CREATED
        // return ResponseEntity.status(HttpStatus.CREATED).body(Cliente);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id){
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok().body(cliente)) //200 OK
                .orElse(ResponseEntity.notFound().build());         // 404 NOT FOUND
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody Cliente nuevoCliente){
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(nuevoCliente.getNombre());
                    cliente.setEmail(nuevoCliente.getEmail());
                    clienteRepository.save(cliente);
                    return ResponseEntity.ok().body(cliente);  //200  OK
                }).orElseGet(() -> {
                    return ResponseEntity.ok().body(nuevoCliente);
                });

    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {

        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return ResponseEntity.noContent().<Void>build();  // 204 NOT CONTENT
                })
                .orElse(ResponseEntity.notFound().build());  // 404 NOT FOUND
    }
}
