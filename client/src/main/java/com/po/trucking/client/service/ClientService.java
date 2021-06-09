package com.po.trucking.client.service;

import com.po.trucking.client.dto.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ClientService {

    Optional<ClientDto> getById(long id);

    ClientDto create(ClientDto clientDto);

    void update(long id, ClientDto clientDto);

    void delete(long id);

    Page<ClientDto> getAll(Pageable pageable);

    List<ClientDto> getAll();
}
