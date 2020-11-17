package com.sicredi.SincronizacaoReceita.util;

import java.time.Instant;

import org.apache.log4j.Logger;

public class ValidarEntradas {

	
	static Logger logger = Logger.getLogger(ValidarEntradas.class);
	
	public String ValidarAgencia(String agencia) {
		try {
			Integer.parseInt(agencia);
			if (agencia.length() == 4) {
				return agencia;
			} else {
				return "";
			}
		} catch (NumberFormatException e) {
			logger.info("Erro na validação da agência: "+agencia+" - "+ Instant.now());
			return "";
		}

	}

	public String ValidarConta(String conta) {
		try {
			Integer.parseInt(conta);
			if (conta.length() == 6) {
				return conta;
			} else {
				return conta;
			}
		} catch (NumberFormatException e) {
			logger.info("Erro na validação da conta: "+conta+" - "+ Instant.now());
			return "";
		}

	}

	public String ValidarSaldo(String saldo) {
		try {
			Double.parseDouble(saldo);
			return saldo;
		} catch (NumberFormatException e) {
			logger.info("Erro na validação do saldo: "+saldo+" - "+ Instant.now());
			return "";
		}

	}

	public String ValidarStatus(String status) {
		if (status.length() == 1) {
			return status;
		} else {
			logger.info("Erro na validação do status: "+status+" - "+ Instant.now());
			return "";
		}
	}
}
