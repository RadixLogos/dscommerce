package com.devsuperior.dscomerce.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscomerce.dto.ProductDTO;
import com.devsuperior.dscomerce.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {	
		ProductDTO response = productService.findProductById(id); 
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAllProducts(Pageable pegeable){
		Page<ProductDTO> response = productService.findAllProducts(pegeable); 
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insertProduct(@RequestBody ProductDTO dto) {
		dto = productService.insertProduct(dto); 
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.id()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto){
		var response = productService.updateProduct(id, dto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}
