package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.categoria;
import com.example.algamoney.api.repository.categoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
		
		@Autowired
		private categoriaRepository categoriaRepository;
		
		@GetMapping
		public ResponseEntity<?> listar(){
			List<categoria> categoria = categoriaRepository.findAll();
			return !categoria.isEmpty() ? ResponseEntity.ok(categoria): ResponseEntity.noContent().build();
		}
		
		@PostMapping
		public ResponseEntity<categoria> criar(@RequestBody categoria categoria, HttpServletResponse response) {
			categoria categoriaSalva = categoriaRepository.save(categoria);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(categoriaSalva.getCodigo()).toUri();
			
			//response.setHeader("location", uri.toASCIIString());
			
			return ResponseEntity.created(uri).body(categoriaSalva);
		}
		
		@GetMapping("/{codigo}")
		public ResponseEntity<?> buscarPeloCodigo(@PathVariable long codigo) {
			Optional<categoria> categoria = categoriaRepository.findById(codigo);
				
		
			return !categoria.isEmpty() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
		
		}
		
}
