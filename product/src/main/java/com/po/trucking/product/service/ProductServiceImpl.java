package com.po.trucking.product.service;

import com.po.trucking.product.dto.ProductDto;
import com.po.trucking.product.model.Product;
import com.po.trucking.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductDto> getById(long id) {
        log.info("In ProductServiceImpl getById {}", id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        log.info("In ProductServiceImpl create {}", productDto);
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public void delete(long id) {
        log.info("In ProductServiceImpl delete {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> getAll() {
        log.info("In ProductServiceImpl getAll");
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public void update(long id, ProductDto productDto) {
        log.info("In ProductServiceImpl update {}", productDto);
        productDto.setId(id);
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
    }

    @Override
    public Page<ProductDto> getAll(Pageable pageable) {
        log.info("In ProductServiceImpl getAll");
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

}
