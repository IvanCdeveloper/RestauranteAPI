package com.example.restauranteapi.service;


import com.example.restauranteapi.model.Cliente;
import com.example.restauranteapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    public Page<Cliente> findAllPageable(Pageable pageable){
        return clienteRepository.findAll(pageable);
    }
}
