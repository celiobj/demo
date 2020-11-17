package com.sicredi.SincronizacaoReceita;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sicredi.SincronizacaoReceita.util.ReceitaService;
import com.sicredi.SincronizacaoReceita.util.ValidarEntradas;

@SpringBootTest(classes = { SpringBatchConfiguration.class })
@RunWith(SpringRunner.class)
public class EnviarContaAgenciaTextoTests {

	ReceitaService receitaService = new ReceitaService();
	ValidarEntradas validar = new ValidarEntradas();
	
	@Test
	public void testeAgencia() throws Exception {
		
		String agencia = "AAAA";
		String conta = "111111";
		String saldo = "34,2";
		String status = "B";
		Assert.assertEquals(false,
				receitaService.atualizarConta(validar.ValidarAgencia(agencia),
						validar.ValidarConta(conta.replace("-", "")),
						Double.parseDouble(validar.ValidarSaldo(saldo.replace(',', '.'))),
						validar.ValidarStatus(status)));
	}

}
