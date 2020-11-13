package com.sicredi.SincronizacaoReceita.model;

public class Conta {
	
	//Objeto Conta
	private String agencia;
	private String conta;
	private String saldo;
	private String status;
	
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public String getSaldo() {
		return saldo;
	}
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Conta(String agencia, String conta, String saldo, String status) {
        this.agencia = agencia;
        this.conta = conta;
        this.saldo = saldo;
        this.status = status;
    }
	public Conta() {
		// TODO Auto-generated constructor stub
	}

}
