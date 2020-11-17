package com.sicredi.SincronizacaoReceita;

import static org.mockito.Mockito.doThrow;

import java.time.Instant;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.sicredi.SincronizacaoReceita.processor.ContaItemProcessor;
import com.sicredi.SincronizacaoReceita.util.ReceitaService;
import com.sicredi.SincronizacaoReceita.util.ValidarEntradas;

@SpringBootTest(classes = { SpringBatchConfiguration.class })
@RunWith(MockitoJUnitRunner.class)
public class NullPointerReceitaService {

	@Mock
	ReceitaService receitaService = new ReceitaService();
	ValidarEntradas validar = new ValidarEntradas();
	Logger logger = Logger.getLogger(ContaItemProcessor.class);
	

	@Test
	public void NullPointer() throws NullPointerException {

		String agencia = "0000";
		String conta = "111111";
		String saldo = "34,2";
		String status = "B";
		
		NullPointerReceitaService nullPointer = Mockito.mock(NullPointerReceitaService.class);
		try {
			doThrow(NullPointerException.class).when(nullPointer).receitaService.atualizarConta(agencia, conta,
					Double.parseDouble(saldo.replace(',', '.')), status);

		} catch (Exception e) {
			logger.error("Dados inválidos: " + e.getMessage() + " - " + Instant.now());
		}
		try {
			nullPointer.receitaService.atualizarConta(agencia, conta, Double.parseDouble(saldo.replace(',', '.')),
					status);
		} catch (Exception e) {
			logger.error("Dados inválidos: " + e.getMessage() + " - " + Instant.now());
		
		}
	}

}
