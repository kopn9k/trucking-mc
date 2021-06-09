package com.po.trucking.product.service;

import com.po.trucking.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    Optional<ProductDto> getById(long id);

    ProductDto create(ProductDto productDto);

    void update(long id, ProductDto productDto);

    void delete(long id);

    Page<ProductDto> getAll(Pageable pageable);

    List<ProductDto> getAll();
}