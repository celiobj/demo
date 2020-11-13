package com.sicredi.SincronizacaoReceita;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.sicredi.SincronizacaoReceita.model.Conta;
import com.sicredi.SincronizacaoReceita.processor.ContaItemProcessor;
import com.sicredi.SincronizacaoReceita.util.ReceitaService;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public TaskExecutor taskExecutor() {
	    return new SimpleAsyncTaskExecutor("spring_batch");
	}

	@Bean
	public FlatFileItemReader<Conta> reader() {
		FlatFileItemReader<Conta> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("contas.csv"));
		reader.setEncoding("ISO-8859-3");
		reader.setName("CSV-Reader");
		reader.setLinesToSkip(1);
		reader.setLineMapper(lineMapper());
		return reader;
	}

	@Bean
	public LineMapper<Conta> lineMapper() {
		DefaultLineMapper<Conta> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

		lineTokenizer.setDelimiter(";");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("agencia", "conta", "saldo", "status");

		ContaFieldSetMapper contaFieldSetMapper = new ContaFieldSetMapper();

		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(contaFieldSetMapper);

		return defaultLineMapper;
	}

	@Bean
	public FlatFileItemWriter<Conta> writer() {
		FlatFileItemWriter<Conta> writer = new FlatFileItemWriter<Conta>();
		writer.setResource(new ClassPathResource("local.csv"));
		// writer.update(new ExecutionContext());
		// writer.setShouldDeleteIfExists(true);
		writer.setEncoding("ISO-8859-3");
		writer.setName("CSV-Writer");
		writer.setForceSync(true);
		// writer.setHeaderCallback(headerCallback);
		writer.setLineAggregator(new DelimitedLineAggregator<Conta>() {
			{
				setDelimiter(";");
				setFieldExtractor(new BeanWrapperFieldExtractor<Conta>() {
					{
						setNames(new String[] { "agencia", "conta", "saldo", "status" });
					}
				});
			}
		});
		return writer;
	}

	@Bean
	public ContaItemProcessor processor() {
		return new ContaItemProcessor();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Conta, Conta>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public Job importarConta() {
		return jobBuilderFactory.get("importarConta")
				.incrementer(new RunIdIncrementer())
				.preventRestart()
				.flow(step1())
				.end()
				.build();
	}
}
