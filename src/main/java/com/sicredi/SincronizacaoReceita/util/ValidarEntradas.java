package com.sicredi.SincronizacaoReceita.util;

public class ValidarEntradas {

	public static String ValidarAgencia(String agencia) {
		try {
			Integer.parseInt(agencia);
			if (agencia.length() == 4) {
				return agencia;
			} else {
				return "";
			}
		} catch (NumberFormatException e) {
			//System.out.println("Erro na validação da agência");
			return "";
		}

	}

	public static String ValidarConta(String conta) {
		try {
			Integer.parseInt(conta);
			if (conta.length() == 6) {
				return conta;
			} else {
				return conta;
			}
		} catch (NumberFormatException e) {
			//System.out.println("Erro na validação da conta");
			return "";
		}

	}

	public static String ValidarSaldo(String saldo) {
		try {
			Double.parseDouble(saldo);
			return saldo;
		} catch (NumberFormatException e) {
			//System.out.println("Erro na validação do saldo");
			return "";
		}

	}

	public static String ValidarStatus(String status) {
		if (status.length() == 1) {
			return status;
		} else {
			//System.out.println("Erro na validação do status");
			return "";
		}
	}
}
