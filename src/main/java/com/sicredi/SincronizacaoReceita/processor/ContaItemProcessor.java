package com.sicredi.SincronizacaoReceita.processor;

import java.time.Instant;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.sicredi.SincronizacaoReceita.model.Conta;
import com.sicredi.SincronizacaoReceita.util.ReceitaService;
import com.sicredi.SincronizacaoReceita.util.ValidarEntradas;


public class ContaItemProcessor implements ItemProcessor<Conta, Conta> {

	ReceitaService receitaService = new ReceitaService();
	ValidarEntradas validar = new ValidarEntradas();
	Conta contaNova;

	@Override
	public Conta process(Conta item) throws Exception {

		Logger logger = Logger.getLogger(ContaItemProcessor.class);

		if (item.getAgencia().equalsIgnoreCase("agencia")) {

			contaNova = new Conta(item.getAgencia(), item.getConta(), item.getSaldo(), item.getStatus());

		} else {
			
			String agencia = validar.ValidarAgencia(item.getAgencia());
			String conta = validar.ValidarConta(item.getConta().replace("-", ""));
			String saldo = validar.ValidarSaldo(item.getSaldo().replace(',', '.'));
			String status = validar.ValidarStatus(item.getStatus());
			try {
				if (receitaService.atualizarConta(agencia, conta, Double.parseDouble(saldo), status)) {
					
					String agenciaRetorno = agencia;
					String contaRetorno = conta.substring(0, 5) + "-" + conta.substring(5);
					String saldoRetorno = saldo.replace('.', ',');
					String statusRetorno = status;
					contaNova = new Conta(agenciaRetorno, contaRetorno, saldoRetorno, statusRetorno);
					logger.info("Exportando a conta: " + contaRetorno + " - " + Instant.now());
					
				} else {
					String contaRetorno = conta.substring(0, 5) + "-" + conta.substring(5);
					logger.error("Erro na exportação da conta: " + contaRetorno + " - " + Instant.now());
				}
				
			}catch (Exception e) {
				logger.error("Dados inválidos: " + e.getMessage() + " - " + Instant.now());
			}
		}
		return contaNova;

	}
}
