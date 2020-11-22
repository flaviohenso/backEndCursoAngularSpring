package com.example.algaworks.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algaworks.api.model.Categoria;
import com.example.algaworks.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED) //retorno Status 201 se conseguir salvar com sucesso no banco
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {// HttpServletResponse é usado para retorno a url de acesso a cetegoria criada
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("/{codigo}")
			.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(categoriaSalva);//Retorna a entidade criada no banco e a uri de acesso a essa entidade
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> categoria(@PathVariable Long codigo) {
		Categoria categoria = categoriaRepository.findById(codigo).orElse(null);// se encontrar retorna caso contrario retorna categoria null
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();// retorna codigo 200 ou 404
	}
}
