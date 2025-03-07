package com.example.restauranteapi.service;

import com.example.restauranteapi.repository.ClienteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements UserDetailsService {

    private ClienteRepository clienteRepository;

    ClienteServiceImpl(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Extrae el usuario de la BD
        return this.clienteRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(username+" no encontrado")
        );

    }
}
