package com.po.trucking.service;

import com.po.trucking.dto.StockDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StockService {

    Optional<StockDto> getById(long id);

    StockDto save(StockDto stockDto);

    void update(StockDto stockDto, long id);

    void delete(long id);

    Page<StockDto> getAll(Pageable pageable);

    List<StockDto> getAll();

}
