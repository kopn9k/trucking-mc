package com.po.trucking.client.repository;

import com.po.trucking.client.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findAllByOwnerId(Pageable pageable, Long OwnerId);
    Optional<Client> findByIdAndOwnerId(long id, Long OwnerId);
    void deleteByIdAndOwnerId (long id, Long OwnerId);
    List<Client> findAllByOwnerId(Long OwnerId);
}