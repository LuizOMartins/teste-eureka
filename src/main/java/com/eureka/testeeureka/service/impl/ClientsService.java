package com.eureka.testeeureka.service.impl;

import java.util.Optional;
import com.eureka.testeeureka.model.Clients;
import org.springframework.stereotype.Service;
import com.eureka.testeeureka.repository.ClientsRepository;

@Service
public class ClientsService {

    private final ClientsRepository clientsRepository;

    public ClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public Clients findById(Long id) {
        Optional<Clients> client = clientsRepository.findById(id);
        return client.orElse(null);
    }

    public Clients save(Clients client) {
        return clientsRepository.save(client);
    }

    public Clients findByEmailOrPhone(String email, String phone) {
        if (email != null) {
            return clientsRepository.findByEmail(email);
        } else if (phone != null) {
            return clientsRepository.findByPhone(phone);
        }
        return null;
    }

}
