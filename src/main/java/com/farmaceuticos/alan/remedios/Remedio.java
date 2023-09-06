package com.farmaceuticos.alan.remedios;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Remedio {

	public Remedio(DadosCadastroRemedio dados) {
		this.nome = dados.nome();
		this.lote = dados.lote();
		this.via = dados.via();
		this.validade = dados.validade();
		this.laboratorio = dados.laboratorio();
		this.quantidade = dados.quantidade();
		this.ativo = true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Enumerated(EnumType.STRING)
	private Via via;
	private String lote;
	private int quantidade;
	private LocalDate validade;
	@Enumerated(EnumType.STRING)
	private Laboratorio laboratorio;
	
	private Boolean ativo;

	public void atualizarInformacoes(@Valid DadosAtualizarRemedio dados) {
		if (dados.nome() != null) {
			this.nome = dados.nome();
		}

		if (dados.via() != null) {
			this.via = dados.via();
		}
		
		if (dados.laboratorio() != null) {
			this.laboratorio = dados.laboratorio();
		}
	
	}

	public void inativar() {
		this.ativo = false;
	}
	
	public void ativar() {
		this.ativo = true;
	}
}
