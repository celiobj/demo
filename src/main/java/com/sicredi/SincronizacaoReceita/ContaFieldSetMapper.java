package com.sicredi.SincronizacaoReceita;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.sicredi.SincronizacaoReceita.model.Conta;

public class ContaFieldSetMapper implements FieldSetMapper<Conta>{

	@Override
	public Conta mapFieldSet(FieldSet fieldSet) throws BindException {
		final Conta conta = new Conta();
        conta.setAgencia(fieldSet.readString("agencia"));
        conta.setConta(fieldSet.readString("conta"));
		conta.setSaldo(fieldSet.readString("saldo"));
		conta.setStatus(fieldSet.readString("status"));
        return conta;
	}

}
