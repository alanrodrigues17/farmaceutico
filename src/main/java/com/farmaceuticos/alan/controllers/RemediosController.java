package com.farmaceuticos.alan.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.farmaceuticos.alan.remedios.DadosAtualizarRemedio;
import com.farmaceuticos.alan.remedios.DadosCadastroRemedio;
import com.farmaceuticos.alan.remedios.DadosDetalhamentoRemedio;
import com.farmaceuticos.alan.remedios.DadosListagemRemedio;
import com.farmaceuticos.alan.remedios.RemedioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import com.farmaceuticos.alan.remedios.Remedio;

@RestController
@RequestMapping("/remedios")
public class RemediosController {
	@Autowired
	RemedioRepository remedioRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> cadastrar(@RequestBody @Valid DadosCadastroRemedio dados, UriComponentsBuilder uriBuilder) {
		var remedio = new Remedio(dados);
		remedioRepository.save(remedio);
		var uri = uriBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri(); 
		return ResponseEntity.created(uri).body(new DadosDetalhamentoRemedio(remedio));
	}

	@GetMapping
	public ResponseEntity<List<DadosListagemRemedio>> listar() {
		var listaRemedio = remedioRepository.findAllByAtivoTrue().stream().map(DadosListagemRemedio::new).toList();
		
		return ResponseEntity.ok(listaRemedio);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> atualizar(@RequestBody @Valid DadosAtualizarRemedio dados) {
		var remedio = remedioRepository.getReferenceById(dados.id());
		remedio.atualizarInformacoes(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio)); 
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluirRemedio(@PathVariable Long id) {
		remedioRepository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/inativar/{id}")
	@Transactional
	public ResponseEntity<Void> inativarRemedio(@PathVariable Long id) {
		var remedio = remedioRepository.getReferenceById(id);
		remedio.inativar();

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/ativar/{id}")
	@Transactional
	public ResponseEntity<Void> reativarRemedio(@PathVariable Long id) {
		var remedio = remedioRepository.getReferenceById(id);
		remedio.ativar();
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoRemedio> detalhar(@PathVariable Long id) {
		var remedio = remedioRepository.getReferenceById(id);

		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}

}
