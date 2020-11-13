package com.sicredi.SincronizacaoReceita.processor;

import org.springframework.batch.item.ItemProcessor;

import com.sicredi.SincronizacaoReceita.model.Conta;
import com.sicredi.SincronizacaoReceita.util.ReceitaService;

public class ContaItemProcessor implements ItemProcessor<Conta, Conta> {

	ReceitaService receitaService = new ReceitaService();
	Conta contaNova;

	@Override
	public Conta process(Conta item) throws Exception {
		String agencia = item.getAgencia();
		String conta = item.getConta().replace("-", "");
		String saldo = item.getSaldo().replace(',', '.');
		String status = item.getStatus();

		if (receitaService.atualizarConta(agencia, conta, Double.parseDouble(saldo), status)) {
			// receitaService.atualizarConta(agencia, conta, Double.parseDouble(saldo),
			// status);
			String agenciaRetorno = agencia;
			String contaRetorno = conta.substring(0, 5) + "-" + conta.substring(5);
			String saldoRetorno = saldo.replace('.', ',');
			String statusRetorno = status;
			contaNova = new Conta(agenciaRetorno, contaRetorno, saldoRetorno, statusRetorno);
		}

		return contaNova;
	}

}
