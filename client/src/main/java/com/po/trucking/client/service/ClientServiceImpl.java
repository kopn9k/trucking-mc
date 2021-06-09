package com.po.trucking.client.service;

import com.po.trucking.client.data.CarriersServiceClient;
import com.po.trucking.client.dto.CarrierDto;
import com.po.trucking.client.dto.ClientDto;
import com.po.trucking.client.model.Client;
import com.po.trucking.client.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;
    private final CarriersServiceClient carriersServiceClient;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper, CarriersServiceClient carriersServiceClient) {
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
        this.carriersServiceClient = carriersServiceClient;
    }

    @Override
    public Optional<ClientDto> getById(long id) {
        log.info("In ClientServiceImpl getById {}", id);
        Optional<Client> optionalClient = clientRepository.findByIdAndOwnerId(id, this.getCarrierId());
        Client client = optionalClient.orElseGet(Client::new);
        ClientDto clientDto = modelMapper.map(client, ClientDto.class);
        CarrierDto carrierDto = getCarrierDtoById(client.getOwnerId());
        clientDto.setOwnerOfClient(carrierDto);
        return Optional.of(clientDto);
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        log.info("In ClientServiceImpl create {}", clientDto);
        Client client = modelMapper.map(clientDto, Client.class);
        client.setOwnerId(this.getCarrierId());
        Client savedClient = clientRepository.save(client);
        return modelMapper.map(savedClient, ClientDto.class);
    }

    @Override
    public void update(long id, ClientDto clientDto) {
        log.info("In ClientServiceImpl update {}", clientDto);
        Client client = modelMapper.map(clientDto, Client.class);
        client.setOwnerId(this.getCarrierId());
        clientRepository.save(client);
    }

    @Override
    public void delete(long id) {
        log.info("In ClientServiceImpl delete {}", id);
        clientRepository.deleteByIdAndOwnerId(id, this.getCarrierId());
    }

    @Override
    public Page<ClientDto> getAll(Pageable pageable) {
        log.info("In ClientServiceImpl getAll");
        Page<Client> clients = clientRepository.findAllByOwnerId(pageable, this.getCarrierId());
        return clients.map(client -> {
            ClientDto clientDto = modelMapper.map(client, ClientDto.class);
            clientDto.setOwnerOfClient(getCarrierDtoById(client.getOwnerId()));
            return clientDto;
        });
    }

    @Override
    public List<ClientDto> getAll() {
        log.info("In ClientServiceImpl getAll");
        List<Client> clients = clientRepository.findAllByOwnerId(this.getCarrierId());
        return clients.stream().map(client -> {
           ClientDto clientDto = modelMapper.map(client, ClientDto.class);
            clientDto.setOwnerOfClient(getCarrierDtoById(client.getOwnerId()));
            return clientDto;
        }).collect(Collectors.toList());
    }

    private Long getCarrierId() {
        Long carrierId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return carrierId;
    }


    private CarrierDto getCarrierDtoById(Long carrierId) {
        ResponseEntity<CarrierDto> carrierResponseEntity = carriersServiceClient.getCarrier(carrierId);
        return carrierResponseEntity.getBody();
    }
}
