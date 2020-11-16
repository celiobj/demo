package com.sicredi.SincronizacaoReceita;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.sicredi.SincronizacaoReceita.model.Conta;
import com.sicredi.SincronizacaoReceita.processor.ContaItemProcessor;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableBatchProcessing
public class SpringBatchApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
		System.out.println(args[0]);
	}
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(64);
		executor.setMaxPoolSize(64);
		executor.setQueueCapacity(64);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setThreadNamePrefix("MultiThreaded-");
		return executor;
	}

	@Bean
	public FlatFileItemReader<Conta> reader() {
		FlatFileItemReader<Conta> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource("C://file//contas.csv"));
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
		writer.setResource(new FileSystemResource("C://file//local.csv"));
		writer.open(new ExecutionContext());
		// writer.update(new ExecutionContext());
		// writer.setShouldDeleteIfExists(true);
		writer.setEncoding("ISO-8859-3");
		writer.setName("CSV-Writer");
		// writer.setForceSync(true);
		// writer.setHeaderCallback(headerCallback);
		// writer.setDelegate(itemWriter())
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
	public AsyncItemWriter<Conta> asyncWriter() {
		AsyncItemWriter<Conta> asyncItemWriter = new AsyncItemWriter<>();
		asyncItemWriter.setDelegate(writer());
		return asyncItemWriter;
	}

	@Bean
	public ContaItemProcessor processor() {
		return new ContaItemProcessor();
	}

	@Bean
	public AsyncItemProcessor<Conta, Conta> asyncProcessor() {
		AsyncItemProcessor<Conta, Conta> asyncItemProcessor = new AsyncItemProcessor<>();
		asyncItemProcessor.setDelegate(itemProcessor());
		asyncItemProcessor.setTaskExecutor(taskExecutor());

		return asyncItemProcessor;
	}

	@Bean
	public ItemProcessor<Conta, Conta> itemProcessor() {
		return (transaction) -> {
			ContaItemProcessor c = new ContaItemProcessor();
			//c.process()
			return transaction;
		};
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Conta, Conta>chunk(10).reader(reader())
				.processor(processor()).writer(writer()).taskExecutor(taskExecutor()).build();
	}

	@Bean
	public Job importarConta() {
		return jobBuilderFactory.get("importarConta").incrementer(new RunIdIncrementer()).preventRestart().flow(step1())
				.end().build();
	}
	
}
