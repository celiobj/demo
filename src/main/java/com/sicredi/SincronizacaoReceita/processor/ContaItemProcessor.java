package com.sicredi.SincronizacaoReceita.processor;

import org.springframework.batch.item.ItemProcessor;

import com.sicredi.SincronizacaoReceita.model.Conta;
import com.sicredi.SincronizacaoReceita.util.ReceitaService;
import com.sicredi.SincronizacaoReceita.util.ValidarEntradas;

public class ContaItemProcessor implements ItemProcessor<Conta, Conta> {

	ReceitaService receitaService = new ReceitaService();
	Conta contaNova;

	@Override
	public  Conta process(Conta item) throws Exception {

		if (item.getAgencia().equalsIgnoreCase("agencia")) {

			contaNova = new Conta(item.getAgencia(), item.getConta(), item.getSaldo(), item.getStatus());

		} else {
			
			String agencia = ValidarEntradas.ValidarAgencia(item.getAgencia());
			String conta = ValidarEntradas.ValidarConta(item.getConta().replace("-", ""));
			String saldo = ValidarEntradas.ValidarSaldo(item.getSaldo().replace(',', '.'));
			String status = ValidarEntradas.ValidarStatus(item.getStatus());

			if (receitaService.atualizarConta(agencia, conta, Double.parseDouble(saldo), status)) {
				
				String agenciaRetorno = agencia;
				String contaRetorno = conta.substring(0, 5) + "-" + conta.substring(5);
				String saldoRetorno = saldo.replace('.', ',');
				String statusRetorno = status;
				contaNova = new Conta(agenciaRetorno, contaRetorno, saldoRetorno, statusRetorno);
				System.out.println("Exportando a conta: " + contaRetorno);
			
			} else {
				
				String contaRetorno = conta.substring(0, 5) + "-" + conta.substring(5);
				System.out.println("Erro na exportação da conta: " + contaRetorno);
			
			}
		}
		return contaNova;
		
	}
}
