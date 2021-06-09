package com.po.trucking.service;

import com.po.trucking.data.CarriersServiceClient;
import com.po.trucking.dto.CarrierDto;
import com.po.trucking.dto.StockDto;
import com.po.trucking.model.Stock;
import com.po.trucking.repository.StockRepository;
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
public class StockServiceImpl implements StockService {

    private final ModelMapper modelMapper;
    private final StockRepository stockRepoSitory;
    private final CarriersServiceClient carriersServiceClient;

    @Autowired
    public StockServiceImpl(StockRepository stockRepoSitory, ModelMapper modelMapper, CarriersServiceClient carriersServiceClient) {
        this.modelMapper = modelMapper;
        this.stockRepoSitory = stockRepoSitory;
        this.carriersServiceClient = carriersServiceClient;
    }

    @Override
    public Optional<StockDto> getById(long id) {
        log.info("In StockServiceImpl getById {}", id);
        Optional<Stock> optionalStock = stockRepoSitory.findByIdAndOwnerId(id, this.getCarrierId());
        Stock stock = optionalStock.orElseGet(Stock::new);
        StockDto stockDto = modelMapper.map(stock, StockDto.class);
        CarrierDto carrierDto = getCarrierDtoById(stock.getOwnerId());
        stockDto.setOwnerOfStock(carrierDto);
        return Optional.of(stockDto);
    }

    @Override
    public StockDto save(StockDto stockDto) {
        log.info("In StockServiceImpl save {}", stockDto);
        Stock stock = modelMapper.map(stockDto, Stock.class);
        stock.setOwnerId(this.getCarrierId());
        Stock savedStock = stockRepoSitory.save(stock);
        return modelMapper.map(savedStock, StockDto.class);
    }

    @Override
    public void update(StockDto stockDto, long id) {
        log.info("In StockServiceImpl update {}", stockDto);
        Stock stock = modelMapper.map(stockDto, Stock.class);
        stock.setOwnerId(this.getCarrierId());
        stockRepoSitory.save(stock);
    }

    @Override
    public void delete(long id) {
        log.info("In StockServiceImpl delete {}", id);
        stockRepoSitory.deleteByIdAndOwnerId(id, this.getCarrierId());
    }

    @Override
    public Page<StockDto> getAll(Pageable pageable) {
        log.info("In StockServiceImpl getAll");
        Page<Stock> stocks = stockRepoSitory.findAllByOwnerId(pageable, this.getCarrierId());
        return stocks.map(stock -> {
            StockDto stockDto = modelMapper.map(stock, StockDto.class);
            stockDto.setOwnerOfStock(getCarrierDtoById(stock.getOwnerId()));
            return stockDto;
        });
    }

    @Override
    public List<StockDto> getAll() {
        log.info("In StockServiceImpl getAll");
        List<Stock> stocks = stockRepoSitory.findAllByOwnerId(this.getCarrierId());
        return stocks.stream().map(stock -> {
            StockDto stockDto = modelMapper.map(stock, StockDto.class);
            stockDto.setOwnerOfStock(getCarrierDtoById(stock.getOwnerId()));
            return stockDto;
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
