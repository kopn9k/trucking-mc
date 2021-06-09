package com.po.trucking.client.controller;

import com.po.trucking.client.dto.ClientDto;
import com.po.trucking.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clients/")
@CrossOrigin
public class ClientRestControllerV1 {

    private final ClientService clientService;

    @Autowired
    public ClientRestControllerV1(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable("id") long clientId) {
        Optional<ClientDto> optionalClientDto = this.clientService.getById(clientId);
        return optionalClientDto
                .map(clientDto -> new ResponseEntity<>(clientDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value = "")
    public ResponseEntity<ClientDto> createClient(@RequestBody @Valid ClientDto clientDto) {

        ClientDto savedClientDto = this.clientService.create(clientDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedClientDto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}")
    public ClientDto updateClient(@PathVariable("id") long id, @RequestBody @Valid ClientDto clientDto) {

        this.clientService.update(id, clientDto);
        return clientDto;
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable("id") long id) {

        this.clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "")
    public Page<ClientDto> getAllClients(Pageable pageable) {
        return  this.clientService.getAll(pageable);
    }

    @GetMapping(value = "all")
    public List<ClientDto> getAllClients() {
        return  this.clientService.getAll();
    }

}

