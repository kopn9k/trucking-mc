package com.po.trucking.controller;

import com.po.trucking.dto.StockDto;
import com.po.trucking.service.StockService;
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
@RequestMapping("/api/v1/stocks/")
@CrossOrigin
public class StockRestControllerV1 {

    private final StockService stockService;

    @Autowired
    public StockRestControllerV1(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<StockDto> getStock(@PathVariable("id") long stockId) {

        Optional<StockDto> optionalStockDto = this.stockService.getById(stockId);
        return optionalStockDto
                .map(stockDto -> new ResponseEntity<>(stockDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value = "")
    public ResponseEntity<StockDto> createStock(@RequestBody @Valid StockDto stockDto) {

        StockDto savedStockDto = this.stockService.save(stockDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedStockDto.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}")
    public StockDto updateStock(@PathVariable("id") long id, @RequestBody @Valid StockDto stockDto) {

        this.stockService.update(stockDto, id);

        return stockDto;

    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<StockDto> deleteStock(@PathVariable("id") long id) {
        this.stockService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "")
    public Page<StockDto> getAllStocks(Pageable pageable) {
        return  this.stockService.getAll(pageable);
    }

    @GetMapping(value = "all")
    public List<StockDto> getAllStocks() {
        return  this.stockService.getAll();
    }

}

