package com.doncorleone.dondelivery.resources;

import java.util.List;

import com.doncorleone.dondelivery.dto.ProductDTO;
import com.doncorleone.dondelivery.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<ProductDTO> list = service.findAll();

        return ResponseEntity.ok().body(list);

    }
}