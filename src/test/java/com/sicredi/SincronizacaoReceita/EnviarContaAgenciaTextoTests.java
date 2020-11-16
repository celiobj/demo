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
	// @Autowired
	// private ApplicationContext context;
	// @Autowired
	// private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void testBeans() throws Exception {
		// SpringBachConfiguration springBatchConfiguration =
		// context.getBean(SpringBatchConfiguration.class);
		// Assert.assertNotNull(springBatchConfiguration);
		// JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
		String agencia = "AAAA";
		String conta = "111111";
		String saldo = "34,2";
		String status = "B";
		Assert.assertEquals(false,
				receitaService.atualizarConta(ValidarEntradas.ValidarAgencia(agencia),
						ValidarEntradas.ValidarConta(conta.replace("-", "")),
						Double.parseDouble(ValidarEntradas.ValidarSaldo(saldo.replace(',', '.'))),
						ValidarEntradas.ValidarStatus(status)));
	}

}
