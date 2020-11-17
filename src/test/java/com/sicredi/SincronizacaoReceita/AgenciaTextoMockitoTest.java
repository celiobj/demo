package com.sicredi.SincronizacaoReceita;

import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.sicredi.SincronizacaoReceita.util.ReceitaService;
import com.sicredi.SincronizacaoReceita.util.ValidarEntradas;

@SpringBootTest(classes = { SpringBatchConfiguration.class })
@RunWith(MockitoJUnitRunner.class)
public class AgenciaTextoMockitoTest {

	@Mock
	ReceitaService receitaService = new ReceitaService();
	ValidarEntradas validar = new ValidarEntradas();

	@Test
	public void passagemTextoAgencia() {

		String agencia = "AAAA";
		String conta = "111111";
		String saldo = "34,2";
		String status = "B";
		try {
			Assert.assertEquals(false, receitaService.atualizarConta(agencia,
					conta.replace("-", ""),
					Double.parseDouble(validar.ValidarSaldo(saldo.replace(',', '.'))),
					status));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			verify(receitaService, Mockito.times(1)).atualizarConta(agencia, conta,
					Double.parseDouble(saldo.replace(',', '.')), status);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
