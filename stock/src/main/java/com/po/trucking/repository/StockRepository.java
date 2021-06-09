package com.po.trucking.repository;

import com.po.trucking.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Page<Stock> findAllByOwnerId(Pageable pageable, Long ownerId);
    Optional<Stock> findByIdAndOwnerId(long id, Long ownerId);
    void deleteByIdAndOwnerId(long id, Long ownerId);
    List<Stock> findAllByOwnerId(Long ownerId);
}