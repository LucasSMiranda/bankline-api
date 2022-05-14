package com.dio.santander.banklineapi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.santander.banklineapi.dto.NovaMovimentacao;
import com.dio.santander.banklineapi.model.Correntista;
import com.dio.santander.banklineapi.model.Movimentacao;
import com.dio.santander.banklineapi.model.MovimentacaoTipo;
import com.dio.santander.banklineapi.repository.CorrentistaRepository;
import com.dio.santander.banklineapi.repository.MovimentacaoRepository;

@Service
public class MovimentacaoService {
	@Autowired
	private MovimentacaoRepository repository;
	
		@Autowired
		private CorrentistaRepository correntistaRepository;
		public void  save(NovaMovimentacao NovaMovimentacao) {
			Movimentacao movimentacao = new Movimentacao();
		
		
		//Double valor = novaMovimentacao.getTipo() ==MovimentacaoTipo.RECEITA ? novaMovimentacao.getValor() : novaMovimentacao.getValor * -1;
		
		Double valor = NovaMovimentacao.getValor();
		if (NovaMovimentacao.getTipo() == MovimentacaoTipo.DESPESA)
			valor = valor * -1; 
		
		movimentacao.setDataHora(LocalDateTime.now());
		movimentacao.setIdConta(NovaMovimentacao.getIdConta());
		movimentacao.setTipo(NovaMovimentacao.getTipo());
		movimentacao.setValor(valor);
		
		Correntista correntista = correntistaRepository.findById(NovaMovimentacao.getIdConta()).orElse(null);
		if (correntista != null) {
			correntista.getConta().setSaldo(correntista.getConta().getSaldo()+ valor);
			correntistaRepository.save(correntista);
			
		}
		repository.save(movimentacao);
		
		}	
}
