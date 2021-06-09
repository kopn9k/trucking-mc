package com.po.trucking.product.controller;

import com.po.trucking.product.dto.ProductDto;
import com.po.trucking.product.service.ProductService;
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
@RequestMapping("/api/v1/products/")
@CrossOrigin
public class ProductRestControllerV1 {



    private final ProductService productService;

    @Autowired
    public ProductRestControllerV1(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") long productId) {
        Optional<ProductDto> optionalProductDto = this.productService.getById(productId);
        return optionalProductDto
                .map(productDto -> new ResponseEntity<>(productDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value = "")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {

        ProductDto savedProductDto = this.productService.create(productDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedProductDto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") long id) {

        this.productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "all")
    public List<ProductDto> getAllProducts() {
        return  this.productService.getAll();
    }
}
